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

        <title>P&R Planner</title>

        <!-- Bootstrap core CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

        <!-- Animate CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">

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
            <jsp:include page="includes/navbar.jsp"/>

            <div id="content">
                <div class="wide">
                    <div class="container">
                        <!--
                            <img class="logo-image" src="./images/gentlogo.png" width="500" />
                        -->
                        <div class="input-background col-sm-12">
                            <form id="form" class="form" action="${contextPath}/route" method="get" accept-charset="ISO-8859-1">
                                <div class="place-inputs col-sm-5">
                                    <div class="form-group">
                                        <!-- <label for="start-input" class="col-sm-2 control-label">Vertrek: </label> -->
                                        <div class="col-sm-12">
                                            <input id="start-input" class="form-control input-lg" type="text" placeholder="vertrek"
                                                   value="${startLocationName}" name="start_name">
                                            <input name="start_id" id="start-id" class="hidden" value="${param["start_id"]}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <!-- <label for="end-input" class="col-sm-2 control-label">Aankomst: </label> -->
                                        <div class="col-sm-12">
                                            <input id="end-input" class="form-control input-lg" type="text" placeholder="aankomst"
                                                   value="${endLocationName}" name="end_name">
                                            <input name="end_id" id="end-id" class="hidden" value="${param["end_id"]}">
                                        </div>
                                    </div>
                                </div>
                                <div id="switcher" class="col-sm-1">
                                    <span id="arrow-switch" class="glyphicon glyphicon-sort" aria-hidden="true"></span>
                                </div>
                                <div class="detail-inputs col-sm-7">
                                    <div id="datetime-label-pane" class="col-md-4 form-group">
                                        <label id="datetime-label">Vertrekken op</label>
                                    </div>
                                    <div class="col-md-5 col-sm-7 form-group">
                                        <input id="date-input" name="date-input" value="${param["date-input"]}" class="form-control input-lg" type="date">
                                    </div>
                                    <div class="col-md-3 col-sm-5 form-group">
                                        <input id="hour-input" name="hour-input" value="${param["hour-input"]}" class="form-control input-lg" type="time">
                                    </div>
                                    <div class="col-sm-12 form-group">
                                        <!-- <input id="submit" class="form-control input-lg" value="Bereken route" type="button"> -->

                                        <button id="submit" type="submit" value="zoek " class="form-control input-lg">
                                            Zoek route
                                            <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
                                        </button>
                                    </div>
                                </div>
                                <div class="col-sm-12 form-group more-options">
                                    <div class="col-sm-12">
                                        <button id="more-options-button" class="btn btn-default" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                                            <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                                            Meer opties
                                        </button>
                                        <c:if test="${errorMessage != null}">
                                            <span class="error-messages pull-right">${errorMessage}</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div id="collapseExample" class="collapse col-sm-12">
                                    <div class="col-sm-3">
                                        <select id="selected-pr" class="form-control" name="selected-pr">
                                            <option value="0" hidden>Selecteer P&R</option>
                                            <option value="0" ${param["selected-pr"] == 0 ? "selected" : ""}>--Automatisch--</option>
                                            <c:forEach items="${parkAndRides}" var="parkAndRide" varStatus="currentIndex">
                                                <option value="${parkAndRide.id}"  ${param["selected-pr"] == parkAndRide.id ? "selected" : "" }>${parkAndRide.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="container results-pane bottom-pane">
                    <div class="starter-template">
                        <div class="row">
                            <div id="loader-general" class="col-xs-12 pane-loader">
                                <div class="loader"></div>
                            </div>
                            <c:choose>
                                <c:when test="${routes != null}">
                                    <div class="col-md-7 head-pane">
                                        <div class="col-md-12 pane">
                                            <div class="result-routes">
                                                <c:forEach items="${routes}" var="route" varStatus="currentIndex">
                                                    <div class="row">
                                                        <div class="col-xs-12 route-option-header"><b>Via P+R ${route.parkAndRide.name}: ${route.getTripDurationFormatted()}</b></div>
                                                    </div>
                                                    <div>
                                                        <c:choose>
                                                            <c:when test="${!route.isAchievable()}">
                                                                <p class="text-danger"><strong>Deze verbinding is mogelijk niet haalbaar wegens een vertraging van het openbaar vervoer.</strong></p>
                                                            </c:when>
                                                        </c:choose>
                                                    </div>
                                                    <div tag="${currentIndex.index}" class="result route-choice ${currentIndex.index == 0 ? 'active-result' : ''}"
                                                         pr-lat="${route.parkAndRide.location.latitude}" pr-long="${route.parkAndRide.location.longitude}">
                                                        <div class="row">
                                                            <div class="col-xs-2 route-row center-text-left start-time">
                                                                <div class="row">
                                                                    <c:choose>
                                                                        <c:when test="${!route.isAchievable()}">
                                                                            <span title="Deze verbinding is mogelijk niet haalbaar wegens vertraging van een vorige bus/tram." class="col-xs-2 glyphicon glyphicon-alert bad-route"></span>
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <div class="col-xs-6 .col-xs-offset-2">
                                                                        ${route.subroutes[0].getFormattedStartTime()}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xs-2">
                                                                <img class="icon-bus center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/car.png" />
                                                                <div>${route.getTotalCarTime()}'</div>
                                                            </div>
                                                            <div class="col-xs-2">
                                                                <img class="icon-bus center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/walking.png" />
                                                                <div>${route.getTotalWalkingTime()}'</div>
                                                            </div>
                                                            <div class="col-xs-2">
                                                                <img class="icon-bus center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/wait.png" />
                                                                <div>${route.getWaitingTime()}'</div>
                                                            </div>
                                                            <c:choose>
                                                                <c:when test="${route.getTotalBusTime()>0 && route.getTotalTramTime()>0}">
                                                                    <div class="col-xs-2">
                                                                        <img class="icon-bus center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/public.png" />
                                                                        <div>${route.getTotalPublicTransportTime()}'</div>
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:choose>
                                                                        <c:when test="${route.getTotalTramTime() > 0}">
                                                                            <div class="col-xs-2">
                                                                                <img class="icon-bus center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/tram.png" />
                                                                                <div>${route.getTotalTramTime()}'</div>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <div class="col-xs-2">
                                                                                <img class="icon-bus center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/bus.png" />
                                                                                <div>${route.getTotalBusTime()}'</div>
                                                                            </div>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <div class="col-xs-2 route-row center-text-left end-time">${route.subroutes[route.subroutes.size()-1].getFormattedEndTime()}</div>
                                                        </div>
                                                    </div>

                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="col-md-12 pane google-map">
                                            <div id="map"></div>
                                        </div>
                                    </div>
                                    <div class="col-md-5 head-pane">
                                        <div class="col-md-12 pane info">
                                            <div class="row">
                                                <div id="park-ride-info">
                                                    <div class="col-xs-offset-1 col-xs-3">
                                                        <img height="50" width="50" src="${contextPath}/resources/images/icons/info.png" />
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${routes != null}">
                                                            <c:forEach items="${routes}" var="route" varStatus="currentIndex">
                                                                <div class="col-xs-6 park-ride-name" tag="${currentIndex.index}">
                                                                    <p><b>P+R ${route.parkAndRide.name}</b></p>
                                                                    <p>${route.parkAndRide.totalCapacity} plaatsen</p>
                                                                    <c:if test="${not route.parkAndRide.paid}">
                                                                        <p>Gratis</p>
                                                                    </c:if>
                                                                </div>
                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                                <c:choose>
                                                    <c:when test="${routes != null}">
                                                        <c:forEach items="${routes}" var="route" varStatus="currentIndex">
                                                            <c:if test="${route.parkAndRide.paid}">
                                                                <div class="col-xs-12 rates" tag="${currentIndex.index}">
                                                                    <c:forEach items="${route.parkAndRide.rates}" var="rate">
                                                                        <c:if test="${not empty rate.notes}">
                                                                            <c:set var="opm" value="true"></c:set>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <table id="table-rates" class="table table-bordered table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Soort ticket</th>
                                                                                <th>Prijs</th>
                                                                                    <c:if test="${opm == true}">
                                                                                    <th>Opmerking</th>
                                                                                    </c:if>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <c:forEach items="${route.parkAndRide.rates}" var="rate">
                                                                                <tr>
                                                                                    <td>
                                                                                        ${rate}
                                                                                    </td>
                                                                                    <td>
                                                                                        &euro; ${rate.price}
                                                                                    </td>
                                                                                    <c:if test="${opm == true}">
                                                                                        <td>
                                                                                            ${rate.notes}
                                                                                        </td>
                                                                                    </c:if>
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>

                                        <div class="col-md-12 pane info">
                                            <div class="row">
                                                <div id="park-ride-info">
                                                    <div class="col-xs-offset-1 col-xs-3">
                                                        <img height="50" width="50" src="${contextPath}/resources/images/icons/cost.png" />
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${routes != null}">
                                                            <c:forEach items="${routes}" var="route" varStatus="currentIndex">
                                                                <div class="col-xs-6 park-ride-name" tag="${currentIndex.index}">
                                                                    <p><b>Kosten</b></p>
                                                                    <c:choose>
                                                                        <c:when test ="${route.parkAndRide.paid || route.getPublicTransportCost() > 0}">
                                                                            <c:if test="${ route.getPublicTransportCost() > 0}">
                                                                                <div>Ticket De Lijn: €${route.getPublicTransportCost()}</div>
                                                                            </c:if>
                                                                            <c:if test="${route.parkAndRide.paid}">
                                                                                <div>${route.getParkAndRideCost()}</div>
                                                                            </c:if>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <div>Geen extra kosten</div>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>
                                                            </c:forEach>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-12 pane result-container">
                                            <c:forEach items="${routes}" var="route" varStatus="currentIndex">
                                                <div class="total-route animated zoomIn" tag="${currentIndex.index}">
                                                    <button id="time-earlier" type="submit" value="vroeger" class="form-control result-button" ${route.isExpeditable() ? '' : 'disabled'}>
                                                        30 min vroeger
                                                        <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
                                                    </button>
                                                    <div class="result-header even">
                                                        <div class="vertical-align-right-panel">${route.subroutes[0].getFormattedStartTime()}</div>
                                                    </div>
                                                    <div id="loader-earlier" class="pane-loader pane-loader-change-time-earlier">
                                                        <div class="loader loader-change-time"></div>
                                                    </div>
                                                    <div class="results">
                                                        <c:forEach begin="0" end="${route.subroutes.size()}" items="${route.subroutes}" var="subroute" varStatus="indexSubroute">
                                                            <div class="result" style="${subroute.isAchievable() ?  '' : 'height: 180px'}">
                                                                    <c:choose>
                                                                    <c:when test="${subroute.getRouteType() == 'walking'}">
                                                                        <div class="color-accent walking"></div>
                                                                    </c:when>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${subroute.getRouteType() == 'bus'}">
                                                                            <div class="color-accent bus"></div>
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${subroute.getRouteType() == 'car'}">
                                                                            <div class="color-accent car"></div>
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${subroute.getRouteType() == 'tram'}">
                                                                            <div class="color-accent tram"></div>
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${!subroute.isAchievable()}">
                                                                            <div style="position: relative; top: 5%;">
                                                                                <p class="text-danger"><strong>Deze verbinding is mogelijk niet haalbaar wegens vertraging van een vorige bus/tram.</strong></p>
                                                                            </div>
                                                                        </c:when>
                                                                    </c:choose>
                                                                <div class="col-xs-2 vertical-align-right-panel" ${subroute.isAchievable() ? "" : "style='top:40%;'"}>
                                                                    ${subroute.getFormattedStartTime()}
                                                                    <c:choose><c:when test="${subroute.hasDelay()}"><span class="text-danger"><strong>+${subroute.getDelayInMinutes()}'</strong></span></c:when></c:choose>
                                                                </div>
                                                                <div class="col-xs-2">
                                                                    <c:choose>
                                                                        <c:when test="${subroute.getRouteType() == 'walking'}">
                                                                            <img class="center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/walking.png" />
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${subroute.getRouteType() == 'bus'}">
                                                                            <img class="center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/bus.png" />
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${subroute.getRouteType() == 'car'}">
                                                                            <img class="center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/car.png" />
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${subroute.getRouteType() == 'tram'}">
                                                                            <img class="center-icon-left" height="30" width="30" src="${contextPath}/resources/images/icons/tram.png" />
                                                                        </c:when>
                                                                    </c:choose>
                                                                    <div>
                                                                        <c:if test="${subroute.getRouteType() == 'tram' || subroute.getRouteType() == 'bus'}">
                                                                            <p class="public-transport-subroute-number" style="background-color: ${subroute.color}">${subroute.number}</p>
                                                                        </c:if>
                                                                        <p class="public-transport-subroute-time">${subroute.getDurationInMinutes()}'</p>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-5 vertical-align-right-panel" ${subroute.isAchievable() ? "" : "style='top:40%;'"}>
                                                                    ${subroute.text}
                                                                </div>
                                                                <div class="col-xs-2 vertical-align-right-panel" ${subroute.isAchievable() ? "" : "style='top:40%;'"}>
                                                                    ${subroute.getFormattedEndTime()}
                                                                </div>

                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                    <div id="loader-later" class="pane-loader pane-loader-change-time-later">
                                                        <div class="loader loader-change-time"></div>
                                                    </div>
                                                    <div class="result-header even">
                                                        <div class="vertical-align-right-panel">${route.subroutes[route.subroutes.size()-1].getFormattedEndTime()}</div>
                                                    </div>
                                                    <button id="time-later" type="submit" value="later" class="form-control result-button">
                                                        30 min later
                                                        <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                                                    </button>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-xs-12" hidden="true"> <!-- Hidden to allow for autocomplete, but don't show map on start -->
                                        <div class="col-md-12 pane google-map">
                                            <div id="map"></div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="includes/footer.jsp"/>
        </div>

        <!-- Placed all scripts at the end of the document so the pages load faster -->
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- If jQuery takes too long to load from Google's server, load from local server -->
        <script>window.jQuery || document.write('<script src="${contextPath}/resources/js/jquery.min.js"><\/script>');</script>

        <!-- Bootstrap core JS -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
            
        <!-- Custom JS: vanvoor zetten omdat hij anders soms kan klagen dat hij deze functie niet vindt -->
        <!-- Date and Time autocomplete: deze JS moet in de view blijven staan aangezien je uit een extern javascript bestand het model niet kan aanspreken -->
        <script src="${contextPath}/resources/js/home.js"></script>        
        <script src="${contextPath}/resources/js/capacityColors.js"></script>
        
        <!-- Google Maps API: deze JS moet in de view blijven staan aangezien je uit een extern javascript bestand het model niet kan aanspreken -->
        <script>
            function initMap() {
                var ghent = {lat: ${city.latitude}, lng: ${city.longitude}};

                var map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 12,
                    center: ghent
                });

                //Handles calculating and displaying the route and autocompleting the input fields
                this.handler = new AutocompleteDirectionsHandler(map);

                //initSearch(map);
                initPrLocations(map);

                var polyLines = [];

                <c:forEach items="${routes}" var="route" varStatus="indexRoute">
                    polyLines[${indexRoute.index}] = [];
                    <c:forEach items="${route.subroutes}" var="subroute">
                        <c:forEach items="${subroute.getEncodedPolyline()}" var="polyLine">
                             polyLines[${indexRoute.index}].push(decodePolyLine("${polyLine}", "${subroute.getRouteType()}"));
                        </c:forEach>
                    </c:forEach>
                </c:forEach>

                initRouteSelection(polyLines, map);
            }

            function decodeLevels(encodedLevelsString) {
                var decodedLevels = [];

                for (var i = 0; i < encodedLevelsString.length; ++i) {
                    var level = encodedLevelsString.charCodeAt(i) - 63;
                    decodedLevels.push(level);
                }
                return decodedLevels;
            }

            function decodePolyLine(path, routeType) {

                var color;

                if(routeType == "walking"){
                    color = "#40A8C4";
                } else if (routeType == "car"){
                    color = "#F76A08";
                } else if (routeType == "bus"){
                    color = "#56B523";
                } else if (routeType == "tram"){
                    color = "#56B523";
                }

                var decodedPath = google.maps.geometry.encoding.decodePath(path);
                var decodedLevels = decodeLevels("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

                var poly = new google.maps.Polyline({
                    path: decodedPath,
                    levels: decodedLevels,
                    strokeColor: color,
                    strokeOpacity: 0.9,
                    strokeWeight: 6
                });

                return poly;
            }

            function initPrLocations(map) {
            <c:forEach items="${parkAndRides}" var="parkAndRide">
                var latLng = new google.maps.LatLng(${parkAndRide.location.latitude}, ${parkAndRide.location.longitude});
                var marker = new google.maps.Marker({
                    position: latLng,
                    title: "${parkAndRide.name}",
                    icon: {
                        path: google.maps.SymbolPath.CIRCLE,
                        scale: 12,
                        strokeWeight: 10,
                        strokeColor: capacityToColorCode(${parkAndRide.totalCapacity}),
                        fillColor: capacityToColorCode(${parkAndRide.totalCapacity}),
                        fillOpacity: 1
                    },
                    label: {
                        text: (${parkAndRide.totalCapacity}).toString(),
                        color: "#FFF"
                    },
                    map: map
                });
            </c:forEach>
            }
        </script>
        <script>
            function displayRoute(polyLines, map, index){
                polyLines.forEach(function(polyLines) {
                        polyLines.forEach(function(poly) {
                            poly.setMap(null);
                        });
                    });

                 polyLines[index].forEach(function(polyLine) {
                    polyLine.setMap(map);
                });

            }

            function initRouteSelection(polyLines, map) {
                // If first rates table belongs to first route in results, show it
                if ($(".rates").first().attr("tag") === "0") {
                    $(".rates").first().show();
                }

                if(polyLines.length > 0){

                    new google.maps.Marker({
                        position: polyLines[0][0].getPath().getAt(0),
                        title: 'Vertrek',
                        label: 'V',
                        map: map
                    });

                    var lastPoly = polyLines[0].length-1;
                    var lastMarker = polyLines[0][lastPoly].getPath().length-1;

                    new google.maps.Marker({
                        position: polyLines[0][lastPoly].getPath().getAt(lastMarker),
                        title: 'Aankomst',
                        label: 'A',
                        map: map
                    });

                    displayRoute(polyLines, map, 0);
                }

                $(".route-choice").click(function () {
                    $(".route-choice").removeClass("active-result");
                    $(this).addClass("active-result");

                    $(".total-route").hide();
                    $(".total-route[tag=" + $(this).attr("tag") + "]").show();

                    $(".park-ride-name").hide();
                    $(".park-ride-name[tag=" + $(this).attr("tag") + "]").show();

                    $(".rates").hide();
                    $(".rates[tag=" + $(this).attr("tag") + "]").show();

                    displayRoute(polyLines, map, $(this).attr("tag"));

                });
            }
        </script>
         <!-- Google Maps API: will call initMap JS function -->
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=${environment.getProperty("GMAPS.KEY")}&libraries=places,geometry&callback=initMap"></script>
    </body>
</html>