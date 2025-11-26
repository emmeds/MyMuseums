<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Imposta il CSS specifico per questa pagina --%>
<c:set var="pageCss" value="css/checkout.css" scope="request" />

<!DOCTYPE html>
<html lang="it">
<%@ include file="includes/header.jspf" %>

<body>

<div class="container">
    <div class="checkout-container">
        <h2>Riepilogo e Pagamento</h2>

        <c:if test="${not empty error}">
            <div class="alert-error">
                ⚠️ <c:out value="${error}"/>
            </div>
        </c:if>

        <div class="summary-box">
            <h3>Museo: <c:out value="${sessionScope.nomeMuseoCheckout}"/></h3>

            <c:if test="${not empty sessionScope.ordineInCorso.biglietti}">
                <p>Data Visita: <c:out value="${sessionScope.ordineInCorso.biglietti[0].dataVisita}"/></p>
            </c:if>

            <c:if test="${not empty requestScope.msgTour}">
                <p><em><c:out value="${requestScope.msgTour}"/></em></p>
            </c:if>

            <hr style="border: 0; border-top: 1px solid rgba(0,0,0,0.1); margin: 1rem 0;">

            <div class="total-price">
                Totale: € <fmt:formatNumber value="${sessionScope.ordineInCorso.importoTotale}" type="number" minFractionDigits="2"/>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/AcquistoServlet" method="post">
            <input type="hidden" name="action" value="paga" />


            <div class="form-group">
                <label>Metodo di Pagamento</label>
                <select id="metodoPagamento" name="metodoPagamento" class="form-control" onchange="gestisciMetodoPagamento()">
                    <option value="carta">Carta di Credito</option>
                    <option value="paypal">PayPal</option>
                </select>
            </div>

            <div id="sezioneCarta">
                <div class="form-group">
                    <label>Intestatario Carta</label>
                    <input type="text" name="intestatario" class="form-control" placeholder="Es. Marco Rossi" required>
                </div>

                <div class="form-group">
                    <label>Numero Carta</label>
                    <input type="text" name="numeroCarta" class="form-control" placeholder="1234567891234567" required maxlength="16" title="Inserisci 16 cifre" oninput="this.value = this.value.replace(/\D/g, '')">
                </div>

                <div style="display: flex; gap: 1rem;">
                    <div class="form-group" style="flex: 1;">
                        <label>Scadenza</label>
                        <input type="text" name="scadenza" class="form-control" placeholder="MM/YY" required maxlength="5">
                    </div>
                    <div class="form-group" style="flex: 1;">
                        <label>CVC</label>
                        <input type="text" name="cvc" class="form-control" placeholder="123" required maxlength="3" oninput="this.value = this.value.replace(/\D/g, '')">
                    </div>
                </div>
        </div>

            <div id="sezionePaypal" style="display: none; margin-bottom: 20px;">
                <p>Hai selezionato PayPal. Clicca su Conferma per procedere.</p>
            </div>

            <button type="submit" class="btn-pay">Conferma e Paga</button>
        </form>
    </div>
</div>

<%@ include file="includes/footer.jspf" %>

<script>
    function gestisciMetodoPagamento() {
        var select = document.getElementById("metodoPagamento");
        var divCarta = document.getElementById("sezioneCarta");
        var divPaypal = document.getElementById("sezionePaypal");

        // Seleziona tutti gli input dentro il div della carta
        var inputCarta = divCarta.querySelectorAll("input");

        if (select.value === "paypal") {
            // Nascondi carta
            divCarta.style.display = "none";
            divPaypal.style.display = "block";

            // Rimuovi 'required' dai campi carta (altrimenti il form non parte)
            inputCarta.forEach(function(el) { el.removeAttribute("required"); });
        } else {
            // Mostra carta
            divCarta.style.display = "block";
            divPaypal.style.display = "none";

            // Rimetti 'required'
            inputCarta.forEach(function(el) { el.setAttribute("required", ""); });
        }
    }
</script>
</body>
</html>