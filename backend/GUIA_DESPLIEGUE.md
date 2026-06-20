# Guía de despliegue — Modex (monolito gratis)

Esta guía asume que ya tienes el código del monolito (`modex-monolith/`)
copiado dentro de tu repo `modex` en GitHub, junto a las carpetas de los
microservicios originales (que puedes dejar ahí o borrar después).

Arquitectura final:
- **Backend**: 1 sola app Spring Boot (el monolito) → Render.com (gratis)
- **Base de datos Mongo**: MongoDB Atlas (gratis, tier M0)
- **Base de datos PostgreSQL**: Render Postgres (gratis)
- **Frontend**: React/Vite → Vercel (gratis)

---

## Paso 1 — Mover el monolito a tu repo

En tu máquina, dentro de la carpeta `ropa-store` (o como se llame tu
repo local de `modex`):

1. Copia la carpeta `modex-monolith` que te entregué dentro de la raíz
   del repo.
2. Verifica que `modex-monolith/pom.xml` y `modex-monolith/src/...`
   existan.

No necesitas borrar `auth-service`, `user-service`, etc. todavía —
pueden convivir en el repo mientras confirmas que el monolito funciona.

---

## Paso 2 — Crear la base de datos MongoDB Atlas (gratis)

1. Ve a https://www.mongodb.com/cloud/atlas/register y crea una cuenta
   (o inicia sesión si ya tienes una).
2. Crea un **cluster gratuito (M0)** — elige la región más cercana a ti
   (por ejemplo, AWS / us-east-1).
3. En **Database Access**, crea un usuario con contraseña (anota el
   usuario y la contraseña).
4. En **Network Access**, agrega `0.0.0.0/0` (permitir desde cualquier
   IP) — es lo más simple para un proyecto de portafolio gratuito.
5. En **Database** → **Connect**, copia el connection string. Se ve así:
   ```
   mongodb+srv://usuario:contraseña@cluster0.xxxxx.mongodb.net/?retryWrites=true&w=majority
   ```
6. Edítalo para que apunte a la base `modex_db`:
   ```
   mongodb+srv://usuario:contraseña@cluster0.xxxxx.mongodb.net/modex_db?retryWrites=true&w=majority
   ```
   Guarda este string completo — lo vas a necesitar como `MONGODB_URI`
   en Render.

---

## Paso 3 — Crear la base de datos PostgreSQL en Render (gratis)

1. Ve a https://dashboard.render.com y crea una cuenta (puedes usar tu
   cuenta de GitHub).
2. Click en **New +** → **PostgreSQL**.
3. Nombre: `modex-db` (o el que prefieras). Plan: **Free**.
4. Una vez creada, en la página de la base de datos copia:
   - **Internal Database URL** (la vas a usar como `DATABASE_URL`)
   - **Username**
   - **Password**

> Nota: el plan free de Postgres en Render expira a los 90 días. Para
> un proyecto de portafolio esto es aceptable — puedes recrearla
> gratis cuando expire.

---

## Paso 4 — Desplegar el backend en Render

1. En el Dashboard de Render: **New +** → **Web Service**.
2. Conecta tu repo de GitHub (`YuniorCasiano/modex`) — quizás te pida
   autorizar acceso a Render desde GitHub.
3. Configuración del servicio:
   - **Root Directory**: `modex-monolith`
   - **Runtime**: Docker (Render detecta el `Dockerfile` automáticamente)
   - **Plan**: Free
4. En la sección **Environment Variables**, agrega estas (botón
   "Add Environment Variable"):

   | Key | Value |
   |---|---|
   | `MONGODB_URI` | el connection string de Atlas del Paso 2 |
   | `DATABASE_URL` | la Internal Database URL del Paso 3 |
   | `DATABASE_USERNAME` | el username de Postgres del Paso 3 |
   | `DATABASE_PASSWORD` | el password de Postgres del Paso 3 |
   | `JWT_SECRET` | una clave larga y aleatoria (ver nota abajo) |
   | `FRONTEND_URL` | déjalo vacío por ahora, lo completas en el Paso 6 |
   | `SPRING_PROFILES_ACTIVE` | `prod` |

   **Nota sobre JWT_SECRET**: usa una clave distinta a la que tenías
   en local por seguridad. Puedes generar una rápida así en PowerShell:
   ```powershell
   -join ((48..57)+(65..90)+(97..122) | Get-Random -Count 48 | %{[char]$_})
   ```

5. Click **Create Web Service**. Render va a clonar el repo, construir
   la imagen Docker y desplegar. La primera build puede tardar varios
   minutos (Maven descarga todas las dependencias).
6. Cuando termine, Render te da una URL como:
   ```
   https://modex-monolith.onrender.com
   ```
   Guárdala — la necesitas para el frontend.

7. Verifica que funciona visitando:
   ```
   https://modex-monolith.onrender.com/actuator/health
   ```
   Debe responder `{"status":"UP"}`.

> **Importante sobre el plan Free de Render**: el servicio "se duerme"
> tras 15 minutos sin tráfico, y la primera petición después de eso
> tarda ~30-50 segundos en responder mientras arranca de nuevo. Esto es
> normal y esperado en el plan gratuito — para un portafolio es
> aceptable, solo coméntalo si alguien lo prueba y nota la demora
> inicial.

---

## Paso 5 — Desplegar el frontend en Vercel

1. Ve a https://vercel.com y crea una cuenta (con GitHub es lo más
   rápido).
2. **Add New** → **Project** → importa el repo `YuniorCasiano/modex`.
3. En la configuración del proyecto:
   - **Root Directory**: `frontend`
   - **Framework Preset**: Vite (Vercel lo detecta automáticamente)
4. En **Environment Variables**, agrega:

   | Key | Value |
   |---|---|
   | `VITE_API_URL` | `https://modex-monolith.onrender.com` (sin barra al final) |

5. Click **Deploy**. Vercel te da una URL como:
   ```
   https://modex.vercel.app
   ```

---

## Paso 6 — Conectar el frontend y el backend (CORS)

Ahora que tienes la URL final de Vercel, vuelve a Render:

1. Ve a tu servicio `modex-monolith` → **Environment**.
2. Edita la variable `FRONTEND_URL` y pon la URL exacta de Vercel:
   ```
   https://modex.vercel.app
   ```
3. Guarda — Render va a redesplegar automáticamente con el nuevo valor.

Sin este paso, el navegador bloqueará las peticiones del frontend al
backend por política de CORS.

---

## Paso 7 — Probar todo en producción

1. Abre tu URL de Vercel.
2. Prueba registrar un usuario nuevo.
3. Prueba iniciar sesión.
4. Prueba ver el catálogo de productos — **estará vacío**, porque es
   una base de datos nueva. Necesitas volver a sembrar los productos
   (igual que hiciste la primera vez vía la API, pero ahora apuntando
   a la URL de Render en vez de `localhost:8084`).
5. Prueba crear un pedido de prueba con la tarjeta simulada
   `4111 1111 1111 1111`.

---

## Notas finales

- **Datos**: como las bases de datos son nuevas, partes de cero. No
  hay manera de migrar los datos de tus contenedores Docker locales a
  Atlas/Render automáticamente sin un paso extra de exportación —
  si quieres migrar los productos que ya tenías, dime y armamos un
  script para exportarlos de tu Mongo local e importarlos a Atlas.
- **El primer usuario admin**: el registro público siempre crea
  usuarios con rol `CLIENTE`. Para tener un usuario `ADMIN` en
  producción, regístralo normal y luego cambia su `role` a `ADMIN`
  directamente desde MongoDB Atlas (pestaña Collections → users).
- **Costo real**: con esta configuración, el hosting completo es
  $0/mes. Las únicas limitaciones son: Postgres expira en 90 días
  (recreable gratis) y el backend se duerme tras inactividad.
