var myApp = angular.module('myApp', ['ngRoute', 'to.base64', 'ngAnimate']);

myApp.config(function($routeProvider,$locationProvider,$compileProvider) {
     $routeProvider
     .when('/detalji/:id', {
	    templateUrl: '/templates/detalji.html',
	    controller: 'DetaljiController'
     })
     .when('/dodaj', {
     	    templateUrl: '/templates/dodaj.html',
     	    controller: 'DodajOsobuController'
     })
     .when('/', {
        templateUrl: '/templates/imenik.html',
        controller: 'ImenikController'
     });
     $locationProvider.html5Mode(true);
     $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|chrome-extension):/);
});

myApp.controller('ImenikController', function ($rootScope, $scope, $http, $routeParams, $route, $location) {

    $scope.api = {
           stranica: 0
    };

    $scope.trazi = {
           kriterij: ""
    };
	
	$scope.api.page = 0;

    $scope.ucitajStranicu = function() {
        $http.get('/imenik/?page='+$scope.api.page).success(function(response){
            $scope.api.ukupnoStranica = response.totalPages;
            $scope.osobe = response.content;
        });
    };

    $scope.sljedecaStranica = function() {
                 if ($scope.api.stranica < $scope.api.ukupnoStranica) {
                     $scope.api.stranica++;
                     $scope.ucitajStranicu();
                 }
     };

    $scope.prethodnaStranica = function() {
                 if ($scope.api.stranica > 0) {
                     $scope.api.stranica--;
                     $scope.ucitajStranicu();
                 }
    };

    $scope.imenikPretraga = function() {

    if($scope.trazi.kriterij==""){
        $scope.ucitajStranicu();
    } else {
        $http.get('//'+$location.host()+'/imenik/trazi/'+$scope.trazi.kriterij).success(function(response){
            $scope.osobe = response;
        });
     }
    };

});


myApp.controller('DetaljiController', function ($scope, $http, $route, $routeParams, $location, $timeout) {

      $scope.detalji = {
        id: "",
        ime: "",
        prezime: "",
        grad: "",
        opis: "",
        slika: "" ,
        kontakt: $scope.kontakti
      };

      $scope.ucitavanjeStyle = null;
      $scope.tipkaloPoruka = "Spremi";

      $http.get('//'+$location.host()+'/imenik/osoba/'+$routeParams.id).success(function(response){
           $scope.detalji = response;
           $scope.kontakti = response.kontakt;
           $scope.slika = "background-image: url(//res.cloudinary.com/hkq9ylmon/image/upload/"+$scope.detalji.slika+");";
       });

      $scope.izbrisiOsobu = function() {
        $http.post('//'+$location.host()+'/imenik/obrisi/'+$routeParams.id).success(function(response){
            $location.path("/");
        });
      };

      $scope.spremiPromjene = function() {

            $scope.ucitavanjeStyle = "disabled";
            $scope.tipkaloPoruka = "Ucitavanje..";

            for(var i = 0; i < $scope.kontakti.length; i++) {
                delete $scope.kontakti[i]['kontaktId'];
            }

            $http.post('//'+$location.host()+'/imenik/update', angular.toJson($scope.detalji), {headers: {'Content-Type': 'application/json'}}).success(function(response){
                 $scope.poruka=true;
                 $scope.ucitavanjeStyle = null;
                 $scope.tipkaloPoruka = "Spremi";
                 $timeout(function () { $scope.poruka = false; }, 6000);
            });
      };

      $scope.novaSlika = function() {
         $scope.detalji.slika = $scope.image.base64;
         $scope.slika = "background-image: url(data:"+$scope.image.filetype+";base64,"+$scope.image.base64+");";
      };

      $scope.dodajKontakt = function() {
              $scope.kontakti.push({'broj':$scope.broj,'opis':$scope.opis,'vrsta':$scope.vrsta});
              $scope.broj = null;
              $scope.opis = null;
              $scope.vrsta = null;
              $scope.dozvoli = true;
      };

      $scope.izbrisiKontakt = function(index){
              $scope.kontakti.splice(index, 1);
      }

});


myApp.controller('DodajOsobuController', function ($scope, $http, $timeout, $location) {

    $scope.kontakti = [];
    $scope.ucitavanjeStyle = null;
    $scope.tipkaloPoruka = "Spremi";

    $scope.form = {
        ime: "",
        prezime: "",
        grad: "",
        opis: "",
        slika: "" ,
        kontakt: $scope.kontakti
    };

    $scope.submitForm = function() {

        $scope.ucitavanjeStyle = "disabled";
        $scope.tipkaloPoruka = "Ucitavanje..";
        $scope.form.slika = $scope.image.base64;

        $http.post('//'+$location.host()+'/imenik/dodaj', $scope.form, {headers: {'Content-Type': 'application/json'}}).success(function(response){
             $scope.kontakti = null;
             $scope.image = null;
             $scope.form.opis = null;
             $scope.form.ime = null;
             $scope.form.prezime = null;
             $scope.form.grad = null;
             $scope.poruka=true;
             $scope.ucitavanjeStyle = null;
             $scope.tipkaloPoruka = "Spremi";
             $timeout(function () { $scope.poruka = false; }, 6000);
        });
    };

    $scope.dodajKontakt = function() {
        $scope.kontakti.push({'broj':$scope.broj,'opis':$scope.opis,'vrsta':$scope.vrsta});
        $scope.broj = null;
        $scope.opis = null;
        $scope.vrsta = null;
        $scope.dozvoli = true;
    };

    $scope.izbrisiKontakt = function(index){
        $scope.kontakti.splice(index, 1);
    }

});