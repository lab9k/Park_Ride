<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="nl">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Favicon for all platforms. See realfavicongenerator.net for FAQ. -->
        <link rel="apple-touch-icon" sizes="180x180" href="${contextPath}/apple-touch-icon.png">
        <link rel="icon" type="image/png" href="${contextPath}/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/png" href="${contextPath}/favicon-16x16.png" sizes="16x16">
        <link rel="manifest" href="${contextPath}/manifest.json">
        <link rel="mask-icon" href="${contextPath}/safari-pinned-tab.svg" color="#7b7b7b">
        <meta name="theme-color" content="#ffffff">

        <title>FAQ - P&R Planner</title>

        <!-- Bootstrap core CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>
        <div id="wrapper">
            <jsp:include page="includes/navbar.jsp" />

            <div id="content">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12 head-pane">
                            <div class="col-md-12 pane">
                                <h1 class="faq-question">Waarvoor dient deze tool?</h1>
                                <div class="faq-answer">
                                    <p>
                                        Met behulp van deze <a href='./' target='_blank'>routeplanner</a> kan u verschillende trajectmogelijkheden weergeven naar uw bestemming in de stad via een P&amp;R locatie.
                                        Naast de heenweg met uw wagen, zal de gebruikte P&amp;R locatie weergegeven worden, met bijhorende natrajectmogelijkheden zoals tram en bus.
                                    </p>
                                </div>
                                <h1 class="faq-question">Wat is Park-and-ride?</h1>
                                <div class="faq-answer">
                                    <p>
                                        Park-and-ride, vaak afgekort doort P&amp;R of P&#43;R, is een parkeervoorziening bedoeld voor automobilisten die vervolgens met het openbaar vervoer of met de fiets verder reizen naar hun bestemming.
                                        Deze voorziening heeft als doel congestie te reduceren en het verkeer efficiënter te laten verlopen.
                                    </p>
                                </div>
                                <h1 class="faq-question">Wat heeft dit te maken met Gent?</h1>
                                <div class="faq-answer">
                                    <p>
                                        Sinds 2014 is de stad Gent bezig met het opzetten van een monitoringssysteem voor de verkeerssituatie.
                                        Dit grootschalig project heeft als doel het verkeer zo vlot en veilig mogelijk te laten verlopen voor alle weggebruikers en hierbij opstroppingen en files te voorkomen.
                                        Tegelijk met de uitvoering van het circulatieplan werd daarom een concreet en ambitieus actieprogramma voorgesteld voor de realisatie van een groot aantal bijkomende P&amp;R plaatsen.
                                        Het is de bedoeling om de langparkeerder maximaal te stimuleren om aan de rand van de stad te parkeren op een P&amp;R locatie.
                                        Dat moet helpen om de parkeerdruk en verkeersdruk in het centrum te beheersen.
                                        Deze hoofddoelstelling richt zich vooral op werknemers, studenten en bezoekers die lang parkeren.
                                    </p>
                                    <p>
                                        Daarom wil de stad het gebruik van Park &amp; Ride locaties promoten.
                                        Aan de rand van de stad zijn verschillende parkings die pogen het verkeer in de binnenstad te reduceren.
                                        Door daar uw auto te parkeren en vervolgens het openbaar vervoer te nemen naar het centrum, wil men het gebrek aan plaats in de stadskern oplossen.
                                    </p>
                                </div>
                                <h1 class="faq-question">Waarom zou ik hier gebruik van maken? Er zijn ook ondergrondse parkings in de binnenstad.</h1>
                                <div class="faq-answer">
                                    <p>
                                        De P&amp;R voorzieningen, gelegen aan de rand van de stad, vangen het verkeer reeds buiten het centrum op en bieden bijkomend een aansluiting met het openbaar vervoer of de fiets.
                                        Het gebruik van een P&amp;R gecombineerd met de aanschaf van een ticket van De Lijn is bovendien een goedkoper alternatief dan bovengronds of ondergronds parkeren in het stadscentrum.
                                    </p>
                                    <p>
                                        Een aantal P&amp;R zijn ook voorzien van extra fietsvoorzieningen die moeten bijdragen aan een betere fietservaring.
                                    </p>
                                </div>
                                <h1 class="faq-question">Welke P&amp;R locaties zijn er in Gent?</h1>
                                <div class="faq-answer">
                                    <p>
                                        Een lijst van alle beschikbare P&amp;R locaties in Gent, met extra bijhorende informatie, kan u vinden op de <a href='./overview' target='_blank'>overzichtspagina</a>.
                                    </p>
                                    <p>
                                        Indien u toch graag zelf uw route plant, kan u <a href="https://stad.gent/sites/default/files/page/documents/parkeerkaart_0.pdf" target="_blank">deze parkeerkaart</a> gebruiken.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="includes/footer.jsp"/>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- If jQuery takes too long to load from Google's server, load from local server -->
        <script>window.jQuery || document.write('<script src="${contextPath}/resources/js/jquery.min.js"><\/script>');</script>

        <!-- Bootstrap core JS -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>