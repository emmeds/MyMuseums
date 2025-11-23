<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<%@ include file="includes/header.jspf" %>
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
                    <div class="museum-image" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                        <img src="${pageContext.request.contextPath}/images/mann.png" alt="Museo Archeologico Nazionale di Napoli">
                    </div>
                    <div class="museum-content">
                        <h3>Museo Archeologico Nazionale Di Napoli</h3>
                        <p class="museum-location">📍 Napoli</p>
                        <p class="museum-description">Uno dei più importanti musei archeologici al mondo.</p>
                        <div class="museum-footer">
                            <span class="price">Da €12.00</span>
                            <button class="btn-primary">Prenota Ora</button>
                        </div>
                    </div>
                </div>
                <div class="museum-card">
                    <div class="museum-image" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
                        <img src="${pageContext.request.contextPath}/images/mav.png" alt="Museo Archeologico Virtuale">
                    </div>
                    <div class="museum-content">
                        <h3>Museo Archeologico Virtuale</h3>
                        <p class="museum-location">📍 Ercolano</p>
                        <p class="museum-description">Museo multimediale dedicato a Pompei ed Ercolano.</p>
                        <div class="museum-footer">
                            <span class="price">Da €9.00</span>
                            <button class="btn-primary">Prenota Ora</button>
                        </div>
                    </div>
                </div>
                <div class="museum-card">
                    <div class="museum-image" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);">
                        <img src="${pageContext.request.contextPath}/images/irpino.png" alt="Museo Provinciale Irpino">
                    </div>
                    <div class="museum-content">
                        <h3>Museo Provinciale Irpino</h3>
                        <p class="museum-location">📍 Avellino</p>
                        <p class="museum-description">Collezioni storiche e archeologiche dell’Irpinia.</p>
                        <div class="museum-footer">
                            <span class="price">Da €5.00</span>
                            <button class="btn-primary">Prenota Ora</button>
                        </div>
                    </div>
                </div>
                <div class="museum-card">
                    <div class="museum-image" style="background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);">
                        <img src="${pageContext.request.contextPath}/images/sangennaro.png" alt="Museo del Tesoro di San Gennaro">
                    </div>
                    <div class="museum-content">
                        <h3>Museo del Tesoro di San Gennaro</h3>
                        <p class="museum-location">📍 Napoli</p>
                        <p class="museum-description">Tesoro storico, culturale e religioso unico.</p>
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
<%@ include file="includes/footer.jspf" %>
