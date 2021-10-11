import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import SearchFlights from "./SearchFlights"

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/flights' component={SearchFlights}/>
                </Switch>
            </Router>
        )
    }
}

export default App;