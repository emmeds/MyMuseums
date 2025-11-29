<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="it">
<%@ include file="includes/header.jspf" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Homepage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/DettaglioMuseo.css">

<body>

<div class="details-container">

    <c:if test="${not empty museo}">

        <!-- Form di acquisto -->
        <form action="AcquistoServlet" method="post" class="booking-form" id="bookingForm">
            <input type="hidden" name="idMuseo" value="${museo.idMuseo}">

            <div class="details-grid">

                <!-- 1. Immagine -->
                <div class="details-image-col">
                    <img src="${pageContext.request.contextPath}${museo.immagine}"
                         alt="${museo.nome}">
                </div>

                <!-- 2. Info e Selezione -->
                <div class="details-info-col">
                    <h1 class="details-title"><c:out value="${museo.nome}"/></h1>
                    <p class="details-location">
                        📍 <c:out value="${museo.via}"/>, <c:out value="${museo.citta}"/> (<c:out value="${museo.cap}"/>)
                    </p>

                    <!-- Data Visita (Unica per tutti i biglietti) -->
                    <div class="form-group">
                        <label>Data Visita:</label>
                        <input type="date" name="dataVisita" class="form-control" required>
                        <select name="orarioVisita" class="form-control" required>
                            <option value="" disabled selected>Seleziona Orario</option>
                            <option value="9:00-11:00">9:00 - 11:00</option>
                            <option value="11:00-13:00">11:00 - 13:00</option>
                            <option value="14:00-16:00">14:00 - 16:00</option>
                            <option value="16:00-18:00">16:00 - 18:00</option>
                        </select>
                    </div>

                    <br>

                    <!-- SELEZIONE BIGLIETTI MULTIPLI -->
                    <div class="selection-box">
                        <div class="selection-header">Seleziona Biglietti</div>

                        <div class="tickets-list">
                            <c:forEach items="${listaTipologie}" var="tipo">
                                <div class="ticket-row">
                                    <div class="ticket-info">
                                        <span class="ticket-name"><c:out value="${tipo.nome}"/></span>
                                        <span class="ticket-price">
                                            € <fmt:formatNumber value="${tipo.prezzo}" type="number" minFractionDigits="2"/>
                                        </span>
                                    </div>
                                    <div class="ticket-quantity">
                                        <!-- Inviamo l'ID della tipologia -->
                                        <input type="hidden" name="idTipologia" value="${tipo.idTipologia}">
                                        <!-- Inviamo la quantità associata -->
                                        <label class="qty-label">Qtà:</label>
                                        <input type="number" name="quantita" class="form-control qty-input"
                                               value="0" min="0" max="20">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <c:if test="${empty listaTipologie}">
                            <p style="color:red;">Biglietti non disponibili.</p>
                        </c:if>
                    </div>

                    <br>

                    <!-- Opzione Tour Guidato -->
                    <div class="selection-box">
                        <div class="selection-header">Servizi Aggiuntivi</div>
                        <label class="custom-radio-item">
                            <input type="checkbox" name="tourGuidato" value="true" style="transform: scale(1.3); margin-right: 10px;">
                            <span class="radio-label-text">
                                Aggiungi Tour Guidato
                                <small style="display:block; color:#777; font-weight:normal;">
                                    Si applica a tutti i biglietti selezionati
                                </small>
                            </span>
                            <span class="radio-price-tag">
                                + € <fmt:formatNumber value="${museo.prezzoTourGuidato}" type="number" minFractionDigits="2"/> / pers.
                            </span>
                        </label>
                    </div>

                    <button type="submit" class="btn-buy">Acquista ora</button>
                    <p id="error-msg" style="color: red; display: none; margin-top: 10px;">Seleziona almeno un biglietto.</p>
                </div>
            </div>

            <!-- Descrizione -->
            <div class="description-box">
                <h3>Informazioni sul Museo</h3>
                <p><c:out value="${museo.descrizione}"/></p>
            </div>

        </form>
    </c:if>

    <c:if test="${empty museo}">
        <!-- ... codice errore ... -->
    </c:if>

</div>

<%@ include file="includes/footer.jspf" %>

<!-- Script per data minima e validazione quantità -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Data minima oggi
        var today = new Date().toISOString().split('T')[0];
        var dateInput = document.getElementsByName("dataVisita")[0];
        if(dateInput) dateInput.setAttribute('min', today);

        // Validazione form: almeno un biglietto > 0
        document.getElementById('bookingForm').addEventListener('submit', function(e) {
            var inputs = document.getElementsByName('quantita');
            var total = 0;
            for(var i=0; i < inputs.length; i++) {
                total += parseInt(inputs[i].value) || 0;
            }
            if(total === 0) {
                e.preventDefault();
                document.getElementById('error-msg').style.display = 'block';
            }
        });
    });
</script>

</body>
</html>