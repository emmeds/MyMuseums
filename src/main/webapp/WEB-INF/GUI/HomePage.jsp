<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyMuseums - Biglietti per Musei</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Homepage.css">
</head>
<body>
    <!-- Header -->
    <header class="header">
        <div class="container">
            <div class="logo">
                <h1>🏛️ MyMuseums</h1>
            </div>
            <nav class="nav">
                <ul>
                    <li><a href="#home">Home</a></li>
                    <li><a href="#musei">Musei</a></li>
                    <li><a href="#eventi">Eventi</a></li>
                    <li><a href="#contatti">Contatti</a></li>
                </ul>
            </nav>
            <div class="header-actions">
                <button class="btn-secondary">Accedi</button>
                <button class="btn-primary">Registrati</button>
            </div>
        </div>
    </header>

    <!-- Hero Section -->
    <section class="hero" id="home">
        <div class="hero-overlay"></div>
        <div class="hero-content">
            <h1 class="hero-title">Scopri l'Arte e la Cultura</h1>
            <p class="hero-subtitle">Acquista i tuoi biglietti per i migliori musei d'Italia in un solo click</p>
            <div class="search-box">
                <input type="text" placeholder="Cerca un museo, una città o un'esposizione...">
                <button class="btn-search">🔍 Cerca</button>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features">
        <div class="container">
            <h2 class="section-title">Perché Scegliere MyMuseums?</h2>
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">🎫</div>
                    <h3>Biglietti Digitali</h3>
                    <p>Ricevi i tuoi biglietti direttamente via email. Niente code, niente stampe.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">💰</div>
                    <h3>Prezzi Convenienti</h3>
                    <p>Offerte esclusive e sconti per studenti, famiglie e gruppi.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">⚡</div>
                    <h3>Accesso Rapido</h3>
                    <p>Salta le code con i nostri biglietti con accesso prioritario.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">🔒</div>
                    <h3>Pagamento Sicuro</h3>
                    <p>Transazioni protette con i massimi standard di sicurezza.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Popular Museums Section -->
    <section class="museums" id="musei">
        <div class="container">
            <h2 class="section-title">Musei Più Popolari</h2>
            <div class="museums-grid">
                <div class="museum-card">
                    <div class="museum-image" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);"></div>
                    <div class="museum-content">
                        <h3>Galleria degli Uffizi</h3>
                        <p class="museum-location">📍 Firenze</p>
                        <p class="museum-description">Una delle più importanti gallerie d'arte del mondo.</p>
                        <div class="museum-footer">
                            <span class="price">Da €20.00</span>
                            <button class="btn-primary">Prenota Ora</button>
                        </div>
                    </div>
                </div>
                <div class="museum-card">
                    <div class="museum-image" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);"></div>
                    <div class="museum-content">
                        <h3>Musei Vaticani</h3>
                        <p class="museum-location">📍 Roma</p>
                        <p class="museum-description">Scopri i capolavori della Cappella Sistina.</p>
                        <div class="museum-footer">
                            <span class="price">Da €17.00</span>
                            <button class="btn-primary">Prenota Ora</button>
                        </div>
                    </div>
                </div>
                <div class="museum-card">
                    <div class="museum-image" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);"></div>
                    <div class="museum-content">
                        <h3>Museo Egizio</h3>
                        <p class="museum-location">📍 Torino</p>
                        <p class="museum-description">La più importante collezione egizia al mondo.</p>
                        <div class="museum-footer">
                            <span class="price">Da €15.00</span>
                            <button class="btn-primary">Prenota Ora</button>
                        </div>
                    </div>
                </div>
                <div class="museum-card">
                    <div class="museum-image" style="background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);"></div>
                    <div class="museum-content">
                        <h3>Museo del Novecento</h3>
                        <p class="museum-location">📍 Milano</p>
                        <p class="museum-description">Arte contemporanea italiana del XX secolo.</p>
                        <div class="museum-footer">
                            <span class="price">Da €10.00</span>
                            <button class="btn-primary">Prenota Ora</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="stats">
        <div class="container">
            <div class="stats-grid">
                <div class="stat-item">
                    <h3 class="stat-number">500+</h3>
                    <p>Musei Partner</p>
                </div>
                <div class="stat-item">
                    <h3 class="stat-number">1M+</h3>
                    <p>Biglietti Venduti</p>
                </div>
                <div class="stat-item">
                    <h3 class="stat-number">4.8★</h3>
                    <p>Valutazione Media</p>
                </div>
                <div class="stat-item">
                    <h3 class="stat-number">24/7</h3>
                    <p>Supporto Clienti</p>
                </div>
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="cta">
        <div class="container">
            <h2>Inizia la Tua Avventura Culturale</h2>
            <p>Registrati ora e ricevi uno sconto del 10% sul tuo primo acquisto!</p>
            <button class="btn-cta">Registrati Gratuitamente</button>
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer" id="contatti">
        <div class="container">
            <div class="footer-grid">
                <div class="footer-column">
                    <h3>🏛️ MyMuseums</h3>
                    <p>La tua porta d'accesso alla cultura italiana.</p>
                </div>
                <div class="footer-column">
                    <h4>Link Utili</h4>
                    <ul>
                        <li><a href="#">Chi Siamo</a></li>
                        <li><a href="#">Come Funziona</a></li>
                        <li><a href="#">FAQ</a></li>
                        <li><a href="#">Blog</a></li>
                    </ul>
                </div>
                <div class="footer-column">
                    <h4>Supporto</h4>
                    <ul>
                        <li><a href="#">Centro Assistenza</a></li>
                        <li><a href="#">Termini e Condizioni</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                        <li><a href="#">Contattaci</a></li>
                    </ul>
                </div>
                <div class="footer-column">
                    <h4>Newsletter</h4>
                    <p>Iscriviti per ricevere offerte esclusive</p>
                    <div class="newsletter-form">
                        <input type="email" placeholder="La tua email">
                        <button>Iscriviti</button>
                    </div>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2025 MyMuseums. Tutti i diritti riservati.</p>
            </div>
        </div>
    </footer>
</body>
</html>