<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="it">
<%@ include file="includes/header.jspf" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Homepage.css">
<script src="${pageContext.request.contextPath}/JS/Homepage.js" defer></script>
    <!-- Hero Section -->
    <section class="hero" id="home">
        <div class="hero-overlay"></div>
        <div class="hero-content">
            <h1 class="hero-title">Scopri l'Arte e la Cultura</h1>
            <p class="hero-subtitle">Acquista i tuoi biglietti per i migliori musei d'Italia in un solo click</p>
            <div class="search-box">
                <form action="${pageContext.request.contextPath}/SearchServlet" method="get" class="search-form">
                    <div class="input-wrap">
                        <input class="search-input" id="search-box" name="query" type="text" placeholder="Dove vuoi recarti? Inserisci una città..." autocomplete="off" aria-label="Cerca" />
                        <ul id="suggestions"
                            role="listbox"
                            aria-label="Suggerimenti di ricerca"
                            aria-live="polite"
                            style="display: none;">
                            <!-- I suggerimenti verranno inseriti dinamicamente qui -->
                        </ul>
                    </div>
                    <button type="submit" class="btn-search">🔍 Cerca</button>
                </form>

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

            <c:forEach items="${musei}" var="museo">
                <a href="${pageContext.request.contextPath}/DettagliMuseoServlet?id=${museo.idMuseo}" class="card-link">
                    <div class="museum-card">
                        <div class="museum-image">
                            <img src="${pageContext.request.contextPath}${museo.immagine}"


                                 alt="${museo.nome}"
                                 style="width: 100%; height: 100%; object-fit: cover; display: block;">
                        </div>
                        <div class="museum-content">
                            <h3><c:out value="${museo.nome}"/></h3>
                            <p class="museum-location">📍 <c:out value="${museo.citta}"/></p>
                            <p class="museum-description"><c:out value="${museo.descrizione}"/></p>
                            <div class="museum-footer">
                                <span class="price">Da €<c:out value="${museo.prezzoTourGuidato}"/></span>
                                <button class="btn-primary">Prenota Ora</button>
                            </div>
                        </div>
                    </div>
                </a>

            </c:forEach>
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
            <p>Registrati ora e inizia ad acquistare!</p>
            <button class="btn-cta">Registrati Gratuitamente</button>
        </div>
    </section>
<%@ include file="includes/footer.jspf" %>
