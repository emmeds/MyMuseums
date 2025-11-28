<%--
  Created by IntelliJ IDEA.
  User: dista
  Date: 24/11/2025
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="pageCss" value="./css/faq/faq.css" scope="request" />

<%-- 2. Includi l'header (che ora leggerà la variabile pageCss) --%>
<%@ include file="../WEB-INF/GUI/includes/header.jspf" %>

<section class="page-hero" >
    <div class="container">
        <h1>Domande Frequenti</h1>
        <p>Trova le risposte ai tuoi dubbi sui biglietti e sulle visite.</p>
    </div>
</section>

<section class="faq-section">
    <div class="container">
        <div class="faq-grid">

            <details class="faq-item">
                <summary>Il mio biglietto è rimborsabile?</summary>
                <div class="faq-answer">
                    <p>Il biglietto non è rimborsabile.</p>
                </div>
            </details>

            <details class="faq-item">
                <summary>Come ricevo i biglietti?</summary>
                <div class="faq-answer">
                    <p>I biglietti vengono inviati immediatamente via email dopo il pagamento.</p>
                </div>
            </details>

            <details class="faq-item">
                <summary>Ci sono sconti?</summary>
                <div class="faq-answer">
                    <p>Sì, in alcuni periodi dell'anno potrai usuffruire di sconti speciali. Rimani aggiornato!.</p>
                </div>
            </details>

            <details class="faq-item">
                <summary>Posso acquistare biglietti per musei di altre regioni oltre alla Campania?</summary>
                <div class="faq-answer">
                    <p>No, questo portale è stato sviluppato per dare valore al territorio campano e rinforzarne ancor di più il turismo.</p>
                </div>
            </details>
        </div>
    </div>
</section>

<%-- 3. Includi il footer --%>
<%@ include file="../WEB-INF/GUI/includes/footer.jspf" %>
