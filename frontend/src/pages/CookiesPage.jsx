export default function CookiesPage({ onNav }) {
    const sectionTitle = { fontFamily:'var(--serif)', fontSize:'1.15rem', fontWeight:700, marginTop:'1.75rem', marginBottom:'0.6rem', color:'var(--c-text)' }
    const p = { fontSize:'0.9rem', color:'var(--c-text2)', lineHeight:1.75, marginBottom:'0.75rem' }

    const TH = { padding:'0.6rem 0.875rem', textAlign:'left', fontSize:'0.72rem', color:'var(--c-text3)', textTransform:'uppercase', letterSpacing:0.6, borderBottom:'1px solid var(--c-border)', fontFamily:'var(--sans)', fontWeight:600 }
    const TD = { padding:'0.7rem 0.875rem', borderBottom:'1px solid var(--c-border)', color:'var(--c-text2)', fontSize:'0.85rem' }

    const cookies = [
        ['modex_token', 'Mantiene tu sesión iniciada (token de acceso)', 'Esencial', '1 hora'],
        ['modex_refresh', 'Permite renovar tu sesión sin volver a iniciar sesión', 'Esencial', '7 días'],
        ['modex_user', 'Guarda tu nombre y rol para mostrar tu cuenta correctamente', 'Esencial', 'Hasta cerrar sesión'],
        ['modex_theme', 'Recuerda tu preferencia de modo claro u oscuro', 'Funcional', 'Persistente'],
        ['modex_wishlist', 'Guarda los productos que marcaste como favoritos', 'Funcional', 'Persistente'],
        ['modex_notifications_*', 'Guarda tus notificaciones de pedidos para mostrarlas localmente', 'Funcional', 'Persistente'],
    ]

    return (
        <div style={{ maxWidth:760, margin:'0 auto' }}>
            <button onClick={() => onNav('catalog')}
                    style={{ background:'none', border:'none', color:'var(--c-text2)', cursor:'pointer', display:'flex', alignItems:'center', gap:6, fontFamily:'var(--sans)', fontSize:'0.875rem', marginBottom:'1.5rem' }}>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><polyline points="15 18 9 12 15 6"/></svg>
                Volver
            </button>

            <h1 style={{ fontFamily:'var(--serif)', fontSize:'2rem', fontWeight:700, marginBottom:'0.4rem' }}>Política de cookies</h1>
            <p style={{ fontSize:'0.82rem', color:'var(--c-text3)', marginBottom:'2rem' }}>Última actualización: junio de 2026</p>

            <div className="card" style={{ padding:'2rem' }}>

                <p style={p}>
                    Esta Política de Cookies explica qué son las cookies y tecnologías similares, cómo
                    las utiliza Modex Plus y qué opciones tienes respecto a su uso.
                </p>

                <div style={sectionTitle}>1. ¿Qué son las cookies?</div>
                <p style={p}>
                    Las cookies son pequeños archivos de texto que un sitio web guarda en tu navegador
                    para recordar información sobre tu visita, como tus preferencias de inicio de sesión
                    o configuración. Modex Plus utiliza el almacenamiento local del navegador
                    (<em>localStorage</em>) de forma equivalente a las cookies tradicionales, con el
                    mismo propósito: recordar tu información entre visitas sin tener que pedírtela de nuevo.
                </p>

                <div style={sectionTitle}>2. Cómo usamos las cookies</div>
                <p style={p}>
                    Clasificamos las cookies y datos que almacenamos en tu navegador en dos categorías:
                </p>
                <p style={p}>
                    <strong>Esenciales:</strong> necesarias para que el Sitio funcione correctamente,
                    como mantener tu sesión iniciada. Sin estas, no podrías acceder a tu cuenta ni
                    realizar pedidos.
                </p>
                <p style={p}>
                    <strong>Funcionales:</strong> mejoran tu experiencia recordando tus preferencias,
                    como el modo oscuro/claro o tu lista de favoritos, pero el Sitio puede funcionar sin
                    ellas (simplemente perderías esas preferencias al recargar la página).
                </p>
                <p style={p}>
                    Modex Plus no utiliza cookies de publicidad ni de rastreo de terceros.
                </p>

                <div style={sectionTitle}>3. Detalle de las cookies que utilizamos</div>
                <div style={{ overflowX:'auto', marginBottom:'1rem' }}>
                    <table style={{ width:'100%', borderCollapse:'collapse' }}>
                        <thead>
                        <tr>
                            <th style={TH}>Nombre</th>
                            <th style={TH}>Propósito</th>
                            <th style={TH}>Tipo</th>
                            <th style={TH}>Duración</th>
                        </tr>
                        </thead>
                        <tbody>
                        {cookies.map(([name, purpose, type, duration]) => (
                            <tr key={name}>
                                <td style={{ ...TD, fontFamily:'monospace', fontSize:'0.78rem' }}>{name}</td>
                                <td style={TD}>{purpose}</td>
                                <td style={TD}>
                                        <span style={{
                                            fontSize:'0.7rem', fontWeight:600, padding:'2px 8px', borderRadius:100,
                                            background: type==='Esencial' ? 'rgba(193,68,14,0.1)' : 'rgba(92,107,46,0.12)',
                                            color: type==='Esencial' ? 'var(--c-primary)' : 'var(--c-secondary)',
                                        }}>{type}</span>
                                </td>
                                <td style={TD}>{duration}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                <div style={sectionTitle}>4. Cómo controlar las cookies</div>
                <p style={p}>
                    Puedes eliminar o bloquear las cookies y datos almacenados en cualquier momento desde
                    la configuración de privacidad de tu navegador. Ten en cuenta que, si eliminas o
                    bloqueas las cookies esenciales, es posible que no puedas iniciar sesión ni utilizar
                    correctamente las funciones de la cuenta en Modex Plus.
                </p>
                <p style={p}>
                    También puedes cerrar tu sesión manualmente desde la sección "Mi perfil", lo cual
                    elimina inmediatamente las cookies de sesión (<code style={{ fontSize:'0.82rem' }}>modex_token</code>,{' '}
                    <code style={{ fontSize:'0.82rem' }}>modex_refresh</code> y{' '}
                    <code style={{ fontSize:'0.82rem' }}>modex_user</code>) de tu navegador.
                </p>

                <div style={sectionTitle}>5. Cookies de terceros</div>
                <p style={p}>
                    Modex Plus no integra actualmente herramientas de analítica, publicidad o redes
                    sociales que instalen cookies de terceros en tu navegador.
                </p>

                <div style={sectionTitle}>6. Cambios a esta política</div>
                <p style={p}>
                    Podemos actualizar esta Política de Cookies si cambiamos la forma en que usamos
                    cookies o tecnologías similares. Cualquier cambio será reflejado en esta página con
                    su correspondiente fecha de actualización.
                </p>

                <div style={sectionTitle}>7. Contacto</div>
                <p style={p}>
                    Si tienes preguntas sobre nuestro uso de cookies, escríbenos a{' '}
                    <span style={{ color:'var(--c-primary)', fontWeight:500 }}>hola@modexplus.com</span>.
                </p>

            </div>
        </div>
    )
}