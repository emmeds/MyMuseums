<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageCss" value="css/portafoglio.css" scope="request" />

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Il tuo Portafoglio - MyMuseums</title>


    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<!-- Header -->
<%@ include file="includes/header.jspf" %>

<main class="portfolio-section">
    <div class="container">

        <div class="portfolio-header">
            <h1 class="portfolio-title">Il tuo Portafoglio</h1>
            <p class="portfolio-subtitle">Storico dei tuoi acquisti e biglietti digitali</p>
        </div>

        <c:choose>
            <c:when test="${not empty ordini}">

                <div class="orders-container">
                    <c:forEach items="${ordini}" var="ordine">

                        <!-- Calcolo: somma (prezzo × quantità) per tutti i biglietti -->
                        <c:set var="sommaPrezziQuantita" value="0" />

                        <c:forEach items="${ordine.biglietti}" var="biglietto" varStatus="bigliettoStatus">
                            <c:set var="tipologiaCorrente" value="${ordine.tipologie[bigliettoStatus.index]}" />
                            <c:set var="lineTotal" value="${tipologiaCorrente.prezzo * biglietto.quantita}" />
                            <c:set var="sommaPrezziQuantita" value="${sommaPrezziQuantita + lineTotal}" />
                        </c:forEach>

                        <!-- Differenza: importoTotale - somma(prezzo × quantità) -->
                        <c:set var="differenza" value="${ordine.importoTotale - sommaPrezziQuantita}" />

                        <div class="order-card" id="order-${ordine.idOrdine}">
                            <!-- Intestazione Ordine (Cliccabile) -->
                            <div class="order-header" onclick="toggleOrder(${ordine.idOrdine})">
                                <div class="order-info">
                                    <div class="order-id">
                                        <span># Ordine ${ordine.idOrdine}</span>
                                    </div>
                                    <div class="order-date">
                                        <span class="date-placeholder" data-iso="${ordine.dataAcquisto}">
                                                ${ordine.dataAcquisto}
                                        </span>

                                                <span class="order-time">Orario visita:${ordine.orarioVisita}</span>


                                    </div>
                                </div>

                                <div class="order-total-wrapper">
                                    <!-- Importo totale pagato -->
                                    <div class="order-price">
                                        Totale: € <fmt:formatNumber value="${ordine.importoTotale}" minFractionDigits="2" maxFractionDigits="2"/>
                                    </div>

                               
                                    <!-- Differenza (importoTotale - somma) -->
                                    <c:if test="${differenza != 0}">
                                        <div class="order-diff" style="font-size: 0.9rem; color: #666;">
                                            Servizi extra: € <fmt:formatNumber value="${differenza}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </div>
                                    </c:if>

                                    <div class="toggle-icon">
                                        <span style="font-size: 14px;">&#9660;</span>
                                    </div>
                                 </div>
                            </div>

                            <!-- Dettagli Biglietti -->
                            <div class="order-details">
                                <div class="tickets-list">

                                    <c:if test="${empty ordine.biglietti}">
                                        <p style="padding: 0 1rem; color: var(--muted); font-size: 0.9rem;">
                                            Nessun dettaglio biglietto disponibile per questo ordine.
                                        </p>
                                    </c:if>

                                    <c:forEach items="${ordine.biglietti}" var="biglietto" varStatus="status">
                                        <div class="ticket-item">
                                            <!-- Recupero la tipologia corrispondente usando l'indice corrente (status.index) -->
                                            <c:set var="tipologiaCorrente" value="${ordine.tipologie[status.index]}" />
                                            <div class="ticket-info">
                                                <h4>${tipologiaCorrente.nome}</h4>
                                                <div class="ticket-meta">

                                                    <span>
                                                        Data Visita: <strong class="date-visit-placeholder" data-iso="${biglietto.dataVisita}">${biglietto.dataVisita}</strong>
                                                    </span>

                                                    <span style="border-left: 1px solid #ccc; padding-left: 10px;">
                                                        Prezzo: € ${tipologiaCorrente.prezzo}
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="ticket-qty">
                                                x${biglietto.quantita}
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </div>

            </c:when>

            <c:otherwise>
                <!-- Stato Vuoto -->
                <div class="empty-state">
                    <div class="empty-icon">🎟️</div>
                    <h3>Nessun ordine trovato</h3>
                    <p>Non hai ancora acquistato nessun biglietto. Inizia a esplorare i nostri musei!</p>
                    <a href="${pageContext.request.contextPath}/#musei" class="btn-primary">
                        Esplora Musei
                    </a>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</main>

<%@ include file="includes/footer.jspf" %>

<script>
    function toggleOrder(id) {
        const card = document.getElementById('order-' + id);
        card.classList.toggle('expanded');
    }

    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.date-placeholder').forEach(el => {
            const isoDate = el.getAttribute('data-iso');
            if(isoDate) {
                const dateObj = new Date(isoDate);
                const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
                el.textContent = dateObj.toLocaleDateString('it-IT', options);
            }
        });

        document.querySelectorAll('.date-visit-placeholder').forEach(el => {
            const isoDate = el.getAttribute('data-iso');
            if(isoDate) {
                const dateObj = new Date(isoDate);
                el.textContent = dateObj.toLocaleDateString('it-IT');
            }
        });
    });
</script>
</body>
</html>