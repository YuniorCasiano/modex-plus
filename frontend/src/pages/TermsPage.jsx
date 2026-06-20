export default function TermsPage({ onNav }) {
    const sectionTitle = { fontFamily:'var(--serif)', fontSize:'1.15rem', fontWeight:700, marginTop:'1.75rem', marginBottom:'0.6rem', color:'var(--c-text)' }
    const p = { fontSize:'0.9rem', color:'var(--c-text2)', lineHeight:1.75, marginBottom:'0.75rem' }
    const li = { fontSize:'0.9rem', color:'var(--c-text2)', lineHeight:1.75, marginBottom:'0.4rem' }

    return (
        <div style={{ maxWidth:760, margin:'0 auto' }}>
            <button onClick={() => onNav('catalog')}
                    style={{ background:'none', border:'none', color:'var(--c-text2)', cursor:'pointer', display:'flex', alignItems:'center', gap:6, fontFamily:'var(--sans)', fontSize:'0.875rem', marginBottom:'1.5rem' }}>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><polyline points="15 18 9 12 15 6"/></svg>
                Volver
            </button>

            <h1 style={{ fontFamily:'var(--serif)', fontSize:'2rem', fontWeight:700, marginBottom:'0.4rem' }}>Términos y condiciones</h1>
            <p style={{ fontSize:'0.82rem', color:'var(--c-text3)', marginBottom:'2rem' }}>Última actualización: junio de 2026</p>

            <div className="card" style={{ padding:'2rem' }}>

                <p style={p}>
                    Estos Términos y Condiciones (los "Términos") regulan el acceso y uso de Modex Plus
                    (el "Sitio", la "Plataforma" o el "Servicio"), operado desde República Dominicana.
                    Al crear una cuenta, navegar el catálogo o realizar un pedido en Modex Plus, aceptas
                    quedar obligado por estos Términos. Si no estás de acuerdo con alguna parte de ellos,
                    te pedimos no utilizar el Servicio.
                </p>

                <div style={sectionTitle}>1. Sobre Modex Plus</div>
                <p style={p}>
                    Modex Plus es una tienda en línea especializada en moda de tallas grandes (plus size)
                    para mujeres, hombres y niños. Ofrecemos un catálogo de prendas, gestión de pedidos,
                    favoritos y cuenta personal, con el objetivo de hacer la moda inclusiva y accesible
                    para todas las siluetas.
                </p>

                <div style={sectionTitle}>2. Cuentas de usuario</div>
                <p style={p}>Para realizar compras en Modex Plus es necesario crear una cuenta. Al registrarte, te comprometes a:</p>
                <ul style={{ paddingLeft:'1.25rem', marginBottom:'0.75rem' }}>
                    <li style={li}>Proporcionar información veraz, completa y actualizada.</li>
                    <li style={li}>Mantener la confidencialidad de tu contraseña y notificarnos de cualquier uso no autorizado de tu cuenta.</li>
                    <li style={li}>Ser responsable de toda actividad que ocurra bajo tu cuenta.</li>
                    <li style={li}>Ser mayor de 18 años, o contar con la autorización de un tutor legal.</li>
                </ul>
                <p style={p}>
                    Nos reservamos el derecho de suspender o cancelar cuentas que infrinjan estos Términos
                    o que muestren actividad fraudulenta o abusiva.
                </p>

                <div style={sectionTitle}>3. Productos y disponibilidad</div>
                <p style={p}>
                    Hacemos nuestro mejor esfuerzo para que las descripciones, tallas, colores e imágenes
                    de los productos sean precisas. Sin embargo, la disponibilidad de stock puede cambiar
                    sin previo aviso, y nos reservamos el derecho de limitar las cantidades disponibles
                    por pedido o de discontinuar cualquier producto en cualquier momento.
                </p>
                <p style={p}>
                    Los precios mostrados están expresados en pesos dominicanos (RD$) e incluyen los
                    impuestos aplicables, salvo que se indique lo contrario. Nos reservamos el derecho
                    de modificar los precios en cualquier momento, sin que esto afecte pedidos ya confirmados.
                </p>

                <div style={sectionTitle}>4. Pedidos y pagos</div>
                <p style={p}>
                    Al confirmar un pedido, recibirás una confirmación con el resumen de tu compra. Un
                    pedido se considera "Confirmado" cuando se verifica la disponibilidad de inventario;
                    de no haber stock suficiente, el pedido será cancelado automáticamente y se te
                    notificará el motivo.
                </p>
                <p style={p}>
                    Modex Plus es un proyecto de demostración con fines de portafolio. El procesamiento
                    de pagos en este sitio es completamente simulado: no se procesan transacciones
                    bancarias reales ni se almacenan datos reales de tarjetas de crédito o débito.
                </p>

                <div style={sectionTitle}>5. Envíos y entregas</div>
                <p style={p}>
                    Los tiempos de entrega estimados se muestran durante el proceso de checkout y pueden
                    variar según la zona de entrega. Modex Plus no se hace responsable por retrasos
                    causados por terceros (transportistas, condiciones climáticas, eventos de fuerza
                    mayor) ajenos a nuestro control directo.
                </p>

                <div style={sectionTitle}>6. Cancelaciones y devoluciones</div>
                <p style={p}>
                    Puedes cancelar un pedido mientras se encuentre en estado "Pendiente" o "Confirmado"
                    desde la sección "Mis pedidos" de tu cuenta. Una vez el pedido haya sido marcado como
                    "Enviado", la cancelación deberá gestionarse contactando a nuestro servicio de atención
                    al cliente.
                </p>

                <div style={sectionTitle}>7. Propiedad intelectual</div>
                <p style={p}>
                    Todo el contenido del Sitio —incluyendo el logotipo, los textos, el diseño visual y
                    el código fuente— es propiedad de Modex Plus o de sus respectivos titulares, y está
                    protegido por las leyes de propiedad intelectual aplicables. Queda prohibida su
                    reproducción total o parcial sin autorización previa por escrito.
                </p>

                <div style={sectionTitle}>8. Limitación de responsabilidad</div>
                <p style={p}>
                    En la máxima medida permitida por la ley, Modex Plus no será responsable por daños
                    indirectos, incidentales o consecuentes derivados del uso o la imposibilidad de uso
                    del Servicio. El Sitio se ofrece "tal cual" y "según disponibilidad", sin garantías
                    de ningún tipo, expresas o implícitas.
                </p>

                <div style={sectionTitle}>9. Modificaciones a estos Términos</div>
                <p style={p}>
                    Podemos actualizar estos Términos periódicamente para reflejar cambios en nuestras
                    prácticas o por motivos legales y operativos. La fecha de la última actualización se
                    indica en la parte superior de esta página. El uso continuado del Servicio después
                    de cualquier modificación constituye tu aceptación de los nuevos Términos.
                </p>

                <div style={sectionTitle}>10. Ley aplicable y jurisdicción</div>
                <p style={p}>
                    Estos Términos se rigen por las leyes de la República Dominicana. Cualquier disputa
                    derivada de su interpretación o cumplimiento será sometida a los tribunales competentes
                    de Santo Domingo, República Dominicana.
                </p>

                <div style={sectionTitle}>11. Contacto</div>
                <p style={p}>
                    Si tienes preguntas sobre estos Términos, puedes escribirnos a{' '}
                    <span style={{ color:'var(--c-primary)', fontWeight:500 }}>hola@modexplus.com</span>.
                </p>

            </div>
        </div>
    )
}