export default function PrivacyPage({ onNav }) {
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

            <h1 style={{ fontFamily:'var(--serif)', fontSize:'2rem', fontWeight:700, marginBottom:'0.4rem' }}>Política de privacidad</h1>
            <p style={{ fontSize:'0.82rem', color:'var(--c-text3)', marginBottom:'2rem' }}>Última actualización: junio de 2026</p>

            <div className="card" style={{ padding:'2rem' }}>

                <p style={p}>
                    En Modex Plus respetamos tu privacidad y nos comprometemos a proteger los datos
                    personales que nos compartes. Esta Política de Privacidad explica qué información
                    recopilamos, cómo la usamos, con quién la compartimos y qué derechos tienes sobre ella.
                </p>

                <div style={sectionTitle}>1. Información que recopilamos</div>
                <p style={p}>Recopilamos la siguiente información cuando usas Modex Plus:</p>
                <ul style={{ paddingLeft:'1.25rem', marginBottom:'0.75rem' }}>
                    <li style={li}><strong>Datos de cuenta:</strong> nombre completo, correo electrónico y contraseña (almacenada de forma cifrada, nunca en texto plano).</li>
                    <li style={li}><strong>Datos de perfil:</strong> ciudad, país y número de teléfono, cuando los proporcionas voluntariamente.</li>
                    <li style={li}><strong>Datos de pedidos:</strong> dirección de envío, historial de compras, productos en tu carrito y lista de favoritos.</li>
                    <li style={li}><strong>Datos técnicos:</strong> dirección IP, tipo de navegador y preferencias de visualización (como el modo oscuro/claro), recopilados automáticamente para el funcionamiento del Sitio.</li>
                </ul>
                <p style={p}>
                    No solicitamos ni almacenamos números completos de tarjetas de crédito o débito, ya
                    que Modex Plus es un proyecto de demostración con procesamiento de pagos simulado.
                </p>

                <div style={sectionTitle}>2. Cómo usamos tu información</div>
                <p style={p}>Usamos los datos que recopilamos para:</p>
                <ul style={{ paddingLeft:'1.25rem', marginBottom:'0.75rem' }}>
                    <li style={li}>Crear y gestionar tu cuenta de usuario.</li>
                    <li style={li}>Procesar y dar seguimiento a tus pedidos.</li>
                    <li style={li}>Enviarte notificaciones sobre el estado de tus pedidos (confirmación, envío, entrega).</li>
                    <li style={li}>Personalizar tu experiencia, como recordar tus preferencias de tema e idioma.</li>
                    <li style={li}>Mejorar el funcionamiento y seguridad de la Plataforma.</li>
                </ul>

                <div style={sectionTitle}>3. Base legal del tratamiento</div>
                <p style={p}>
                    Tratamos tus datos personales con base en la ejecución del contrato que se establece
                    al crear una cuenta y realizar pedidos en Modex Plus, así como en tu consentimiento
                    explícito al momento del registro, y en nuestro interés legítimo en mantener la
                    seguridad y mejorar el Servicio.
                </p>

                <div style={sectionTitle}>4. Con quién compartimos tu información</div>
                <p style={p}>
                    No vendemos ni alquilamos tu información personal a terceros. Tus datos pueden ser
                    procesados por proveedores de infraestructura tecnológica que utilizamos para operar
                    el Servicio (como servicios de hosting y bases de datos en la nube), quienes están
                    obligados contractualmente a proteger tu información y usarla únicamente para los
                    fines que les encomendamos.
                </p>

                <div style={sectionTitle}>5. Seguridad de la información</div>
                <p style={p}>
                    Implementamos medidas técnicas razonables para proteger tus datos, incluyendo el
                    cifrado de contraseñas y el uso de conexiones seguras (HTTPS) para la transmisión de
                    información. Ninguna plataforma en internet puede garantizar seguridad absoluta, pero
                    trabajamos continuamente para minimizar riesgos.
                </p>

                <div style={sectionTitle}>6. Retención de datos</div>
                <p style={p}>
                    Conservamos tu información personal mientras tu cuenta permanezca activa o sea
                    necesario para cumplir con los fines descritos en esta Política. Si solicitas la
                    eliminación de tu cuenta, tus datos personales serán eliminados o anonimizados,
                    salvo aquella información que debamos conservar por obligaciones legales.
                </p>

                <div style={sectionTitle}>7. Tus derechos</div>
                <p style={p}>Como usuario de Modex Plus, tienes derecho a:</p>
                <ul style={{ paddingLeft:'1.25rem', marginBottom:'0.75rem' }}>
                    <li style={li}>Acceder a los datos personales que tenemos sobre ti.</li>
                    <li style={li}>Solicitar la corrección de información inexacta o incompleta, desde tu sección de "Mi perfil".</li>
                    <li style={li}>Solicitar la eliminación de tu cuenta y datos asociados.</li>
                    <li style={li}>Retirar tu consentimiento para el tratamiento de tus datos en cualquier momento.</li>
                </ul>
                <p style={p}>
                    Para ejercer cualquiera de estos derechos, puedes escribirnos a{' '}
                    <span style={{ color:'var(--c-primary)', fontWeight:500 }}>hola@modexplus.com</span>.
                </p>

                <div style={sectionTitle}>8. Menores de edad</div>
                <p style={p}>
                    Modex Plus no está dirigido a menores de 18 años. No recopilamos conscientemente
                    información personal de menores. Si tienes conocimiento de que un menor ha creado
                    una cuenta sin supervisión de un tutor, contáctanos para proceder a su eliminación.
                </p>

                <div style={sectionTitle}>9. Cambios a esta Política</div>
                <p style={p}>
                    Podemos actualizar esta Política de Privacidad ocasionalmente para reflejar cambios
                    en nuestras prácticas de datos. Te recomendamos revisar esta página periódicamente.
                    La fecha de la última actualización se indica en la parte superior.
                </p>

                <div style={sectionTitle}>10. Contacto</div>
                <p style={p}>
                    Si tienes preguntas sobre esta Política de Privacidad o sobre cómo manejamos tus
                    datos, escríbenos a{' '}
                    <span style={{ color:'var(--c-primary)', fontWeight:500 }}>hola@modexplus.com</span>.
                </p>

            </div>
        </div>
    )
}