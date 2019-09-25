package com.build38.fly

class MainPresenter {

    private var view: SearchFlightsViewInterface? = null

    fun onViewAttached(view: SearchFlightsViewInterface) {
        this.view = view
    }

    fun onViewDetached() {
        this.view = null
    }

    fun onUserClickedSearchFlight() {

    }
}
