package com.example.weatherday.router

class MockRouter: Router {
    override fun navegar(ruta: Ruta) {
        println("navegar a : ${ ruta.id }" )
    }
}