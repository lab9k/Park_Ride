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

        <title>Overzicht - P&R Planner</title>

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
                            <div class="col-md-12 pane google-map">
                                <div id="map"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12 head-pane">
                            <div id="pane-overview" class="col-md-12 pane">
                                <c:choose>
                                    <c:when test="${empty parkAndRides}">
                                        Geen  P&R locaties gevonden.
                                    </c:when>
                                    <c:otherwise>
                                        <div id="search" class="form-group">
                                            <label for="search-input" class="col-sm-2 control-label">Zoeken:</label>
                                            <input class="form-control input-lg" type="search" aria-controls="prtable" placeholder="naam, betalend, ...">
                                        </div>
                                        <table id="prtable" class="table table-responsive">
                                            <thead>
                                                <tr>
                                                    <th>Naam</th>
                                                    <th>
                                                        <img class="icon-bus" title="Aantal parkeerplaatsen"
                                                            height="30" width="30" src="${contextPath}/resources/images/icons/car.png" />
                                                    </th>
                                                    <th>
                                                        <span class="glyphicon glyphicon-euro" title="Betalend?" aria-hidden="true"></span>
                                                    </th>
                                                    <th>
                                                        <img class="icon-bus" title="Fiets beschikbaar?"
                                                            height="30" width="30" src="${contextPath}/resources/images/icons/bicycle.png" />
                                                    </th>
                                                    <th>
                                                        <img class="icon-bus" title="Infofiche"
                                                            height="30" width="30" src="${contextPath}/resources/images/icons/info.png" />
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${parkAndRides}" var="parkAndRide" varStatus="currentIndex">
                                                    <tr class="id${parkAndRide.id}">
                                                        <td class="col-sm-2">${parkAndRide.name}</td>
                                                        <td class="col-sm-2">
                                                            <div class="circle">
                                                                ${parkAndRide.totalCapacity}
                                                            </div>
                                                        </td>
                                                        <td class="col-sm-2">
                                                            <c:choose>
                                                                <c:when test="${parkAndRide.paid}">
                                                                    <span class="glyphicon glyphicon-ok prtable-icon" aria-hidden="true" title="Betalend"></span>
                                                                    <span class="prtable-text">Betalend</span>
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                        <td class="col-sm-2">
                                                            <c:choose>
                                                                <c:when test="${parkAndRide.bike}">
                                                                    <span class="glyphicon glyphicon-ok prtable-icon" aria-hidden="true" title="Beschikbaar"></span>
                                                                    <span class="prtable-text">Beschikbaar</span>
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                        <td class="col-sm-2">
                                                            <c:choose>
                                                                <c:when test="${not empty parkAndRide.url}">
                                                                    <a href="${parkAndRide.url}" target="_blank">
                                                                        <span>
                                                                            <span class="prtable-text">infofiche</span>
                                                                            <img class="prtable-image" height="20" width="20" src="${contextPath}/resources/images/icons/external_link.png" title="Infofiche"/>
                                                                        </span>
                                                                    </a>
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </div>
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

        <!-- Datatables JS -->
        <script type="text/javascript" src="https://cdn.datatables.net/v/bs/dt-1.10.14/datatables.min.js"></script>
        
        <!-- Custom JS: vanvoor zetten omdat hij anders soms kan klagen dat hij deze functie niet vindt -->
        <script src="${contextPath}/resources/js/capacityColors.js"></script>

        <!-- Google Maps API: deze JS moet in de view blijven staan aangezien je uit een extern javascript bestand het model niet kan aanspreken -->
        <script>
            function initMap() {
                var ghent = {lat: ${city.latitude}, lng: ${city.longitude}};
                var map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 12,
                    center: ghent
                });
                var infoWindow = new google.maps.InfoWindow();
                var markers = [];

                initPrLocations(map, infoWindow, markers);

                $('#prtable').DataTable().order([0, 'asc']).draw(); //Set first order
                //$('#prtable > tbody > tr:first-child').click(); //Select first row
            }

            function initPrLocations(map, infoWindow, markers) {
                <c:forEach items="${parkAndRides}" var="parkAndRide">
                    var latLng = new google.maps.LatLng(${parkAndRide.location.latitude}, ${parkAndRide.location.longitude});
                    markers[${parkAndRide.id}] = new google.maps.Marker({
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

                    markers[${parkAndRide.id}].addListener("click", function() {
                        infoWindow.close();
                        setInfoWindowContent(infoWindow, '${parkAndRide.name}', '${parkAndRide.totalCapacity}', '${parkAndRide.paid}', '${parkAndRide.bike}');
                        infoWindow.open(map, this);
                        
                        //Just using JQuery doesn't work, due to the filtering. It can lead to more than 1 row being active.
                        //$('#prtable > tbody > tr.active-row').removeClass('active-row');
                        //$('#prtable > tbody > tr.id${parkAndRide.id}').addClass('active-row');
                        
                        //This does work, because it accesses the rows via the datatable object.
                        $($('#prtable').DataTable().row('tr.active-row').node()).removeClass('active-row');
                        $($('#prtable').DataTable().row('tr.id${parkAndRide.id}').node()).addClass('active-row');
                    });

                    $('#prtable > tbody > tr.id${parkAndRide.id}').click(function () {
                        infoWindow.close();
                        setInfoWindowContent(infoWindow, '${parkAndRide.name}', '${parkAndRide.totalCapacity}', '${parkAndRide.paid}', '${parkAndRide.bike}');
                        infoWindow.open(map, markers[${parkAndRide.id}]);
                        
                        //Just using JQuery doesn't work, due to the filtering. It can lead to more than 1 row being active.
                        //$('#prtable > tbody > tr.active-row').removeClass('active-row');
                        //$(this).addClass('active-row');
                        
                        //This does work, because it accesses the rows via the datatable object.
                        $($('#prtable').DataTable().row('tr.active-row').node()).removeClass('active-row');
                        $($('#prtable').DataTable().row('tr.id${parkAndRide.id}').node()).addClass('active-row');
                    });
                </c:forEach>
            }

            function setInfoWindowContent(infoWindow, name, totalCapacity, paid, bike) {
                var content =
                    '<div>' +
                        '<img class="markerInfoImg" height="30" width="30" src="${contextPath}/resources/images/icons/info.png" />' +
                        '<div class="markerInfo">' +
                            '<p><b>P+R ' + name + '</b></p>' +
                            '<p>' + totalCapacity + ' plaatsen</p>';
                                if(paid === 'false') {
                                    content += '<p>Gratis</p>';
                                } else {
                                    content += '<p><u>Betalend</u></p>';
                                }
                                if(bike === 'true') {
                                    content += '<p>Fiets beschikbaar</p>';
                                } else {
                                    content += '<p><u>Geen</u> fiets beschikbaar</p>';
                                }
                        content += '</div>' +
                    '</div>';
                infoWindow.setContent(content);
            }
        </script>
        <!-- Google Maps API: will call initMap javascript function -->
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=${environment.getProperty("GMAPS.KEY")}&libraries=places&callback=initMap"></script>

        <!-- Custom JS -->
        <script src="${contextPath}/resources/js/overview.js"></script>
    </body>
</html>