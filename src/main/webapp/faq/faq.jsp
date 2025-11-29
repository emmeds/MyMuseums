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

>

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
<!-- Sezione Guida: Come Visitare i Musei -->
<section class="guide-section">
    <div class="container">
        <h2 class="guide-title">Come Visitare i Musei</h2>
        <p class="guide-intro">Segui questa guida passo-passo per acquistare i tuoi biglietti e accedere ai musei della Campania in modo semplice e veloce.</p>

        <div class="guide-steps">

            <!-- Step 1: Registrazione -->
            <div class="guide-step">
                <div class="step-number">1</div>
                <div class="step-content">
                    <h3>Crea il tuo Account</h3>
                    <p>Se è la tua prima visita, registrati sulla piattaforma MyMuseums:</p>
                    <ul>
                        <li>Clicca sul pulsante <strong>"Registrati"</strong> in alto a destra</li>
                        <li>Compila il modulo con i tuoi dati: nome, cognome, email e password</li>
                        <li>La password deve contenere almeno 8 caratteri, una lettera maiuscola, un numero e un carattere speciale</li>
                        <li>Conferma la registrazione e accedi con le tue credenziali</li>
                    </ul>
                    <p class="step-note"><strong>Nota:</strong> Se hai già un account, effettua semplicemente il login.</p>
                </div>
            </div>

            <!-- Step 2: Scegliere il Museo -->
            <div class="guide-step">
                <div class="step-number">2</div>
                <div class="step-content">
                    <h3>Scegli il Museo da Visitare</h3>
                    <p>Esplora i musei disponibili e trova quello che preferisci:</p>
                    <ul>
                        <li>Naviga nella homepage per vedere i musei in evidenza</li>
                        <li>Usa la <strong>barra di ricerca</strong> per cercare un museo specifico o una città</li>
                        <li>Clicca sulla card del museo per visualizzare tutti i dettagli: descrizione, indirizzo, prezzi e opzioni disponibili</li>
                        <li>Leggi le informazioni sulle tipologie di biglietto: Standard, Ridotto e Salta Fila</li>
                    </ul>
                </div>
            </div>

            <!-- Step 3: Acquisto Biglietti -->
            <div class="guide-step">
                <div class="step-number">3</div>
                <div class="step-content">
                    <h3>Acquista i tuoi Biglietti</h3>
                    <p>Una volta scelto il museo, procedi all'acquisto:</p>
                    <ul>
                        <li>Seleziona la <strong>data della visita</strong> utilizzando il calendario</li>
                        <li>Scegli l'<strong>orario di ingresso</strong> preferito</li>
                        <li>Indica la <strong>quantità di biglietti</strong> per ogni tipologia:
                            <ul class="nested-list">
                                <li><strong>Standard:</strong> biglietto d'ingresso normale</li>
                                <li><strong>Ridotto:</strong> per studenti, anziani e categorie speciali (potrebbero essere richiesti documenti all'ingresso)</li>
                                <li><strong>Salta Fila:</strong> accesso prioritario senza attese</li>
                            </ul>
                        </li>
                        <li>Se disponibile, aggiungi l'opzione <strong>Tour Guidato</strong> per una visita accompagnata</li>
                        <li>Controlla il riepilogo dell'ordine con il totale da pagare</li>
                        <li>Clicca su <strong>"Procedi all'acquisto"</strong></li>
                    </ul>
                    <p class="step-note"><strong>Importante:</strong> Dopo il pagamento riceverai una conferma via email con i dettagli dell'ordine e i biglietti digitali.</p>
                </div>
            </div>

            <!-- Step 4: Accedere al Portafoglio -->
            <div class="guide-step">
                <div class="step-number">4</div>
                <div class="step-content">
                    <h3>Visualizza i Biglietti nel Portafoglio</h3>
                    <p>Tutti i tuoi biglietti acquistati sono accessibili dal Portafoglio:</p>
                    <ul>
                        <li>Effettua il <strong>login</strong> con le tue credenziali</li>
                        <li>Clicca sull'icona utente in alto a destra e seleziona <strong>"Portafoglio"</strong></li>
                        <li>Visualizzerai lo storico di tutti i tuoi ordini con:
                            <ul class="nested-list">
                                <li>Numero d'ordine</li>
                                <li>Data e orario di acquisto</li>
                                <li>Data e orario della visita</li>
                                <li>Importo totale pagato</li>
                                <li>Dettagli di ogni biglietto (tipologia, quantità, prezzo)</li>
                            </ul>
                        </li>
                        <li>Clicca su un ordine per espandere e visualizzare tutti i dettagli dei biglietti</li>
                    </ul>
                </div>
            </div>

            <!-- Step 5: Presentarsi al Museo -->
            <div class="guide-step">
                <div class="step-number">5</div>
                <div class="step-content">
                    <h3>Presentati al Museo</h3>
                    <p>Il giorno della visita, segui queste istruzioni per accedere al museo:</p>
                    <ul>
                        <li><strong>Accedi al tuo Portafoglio</strong> da smartphone, tablet o computer</li>
                        <li>Apri l'ordine corrispondente alla visita di oggi</li>
                        <li><strong>Mostra i biglietti digitali</strong> al personale all'ingresso del museo direttamente dallo schermo del tuo dispositivo</li>
                        <li>Il personale verificherà:
                            <ul class="nested-list">
                                <li>Il numero d'ordine e i codici dei biglietti</li>
                                <li>La data e l'orario della visita</li>
                                <li>La quantità e le tipologie di biglietti acquistati</li>
                            </ul>
                        </li>
                        <li>Se hai acquistato biglietti <strong>ridotti</strong>, porta con te un documento d'identità valido per confermare il diritto alla riduzione</li>
                        <li>Se hai scelto l'opzione <strong>Tour Guidato</strong>, presentati all'orario indicato al punto di ritrovo specificato dal museo</li>
                    </ul>
                    <p class="step-note"><strong>Consiglio:</strong> Arriva al museo 10-15 minuti prima dell'orario prenotato, specialmente se hai un biglietto standard (non Salta Fila).</p>
                </div>
            </div>

            <!-- Step 6: Consigli Utili -->
            <div class="guide-step highlight">
                <div class="step-number">💡</div>
                <div class="step-content">
                    <h3>Consigli Utili</h3>
                    <ul>
                        <li><strong>Salva i biglietti offline:</strong> Fai uno screenshot del Portafoglio o stampa i biglietti in caso di assenza di connessione internet</li>
                        <li><strong>Controlla gli orari di apertura:</strong> Verifica sul sito ufficiale del museo eventuali chiusure straordinarie o modifiche agli orari</li>
                        <li><strong>Pianifica la visita:</strong> Alcuni musei richiedono tempo per essere visitati completamente; calcola almeno 2-3 ore per una visita completa</li>
                        <li><strong>Accessibilità:</strong> Contatta il museo in anticipo se hai esigenze specifiche di accessibilità</li>
                        <li><strong>Biglietti non rimborsabili:</strong> I biglietti acquistati non sono rimborsabili, quindi assicurati di poter essere presente nella data scelta</li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</section>

<%-- 3. Includi il footer --%>
<%@ include file="../WEB-INF/GUI/includes/footer.jspf" %>
