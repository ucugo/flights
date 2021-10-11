import React, { Component } from 'react';
import { Button, Container, Form, FormGroup, Input, Label, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';

class SearchFlights extends Component {

    emptyItem = {
        searchDate: '',
        hasData: false,
        responseData: []
    };

    response1 = []

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        this.response1 = []
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        })

        if (!this.state.item.searchDate.match('^\\d{4}-\\d{2}-\\d{2}$')) {
            alert('Date must be in this format (yyyy-mm-dd)')
            const {item} = this.state;
            item.hasData = false;
            return
        }

        const response = await fetch('/flights/schedule/' + (this.state.item.searchDate) , {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            // body: JSON.stringify(item),
        });
        const data = await response.json();
        this.setState({ totalReactPackages: data.total })
        const {item} = this.state;
        item.hasData = true;
        this.response1 = data
        this.props.history.push('/flights');
    }

    render() {

        const {item} = this.state;
        const title = <h2>Flight Search</h2>;


        return(
            <div>
                <AppNavbar/>
                <Container>
                    {title}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="searchDate">Enter Date</Label>
                            <Input type="text" name="searchDate" id="date" value={item.searchDate || ''}
                                   onChange={this.handleChange} placeholder="yyyy-mm-dd" autoComplete="searchDate"/>
                        </FormGroup>
                        {'\u00A0'}
                        <FormGroup>
                            <Button color="primary" type="submit">Search</Button>{' '}
                        </FormGroup>
                    </Form>
                </Container>
                <div/>
                {'\u00A0'}
                <Container>
                    {item.hasData ?

                        <Table>
                            <thead>
                            <tr>
                                <th>Departure Time</th>
                                <th>Destination</th>
                                <th>Destination Airport</th>
                                <th>flight Number</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.response1.map((item) => (
                                    <tr key={item.id}>
                                        <td>{item.departure_time}</td>
                                        <td>{item.destination}</td>
                                        <td>{item.destination_airport}</td>
                                        <td>{item.flight_number}</td>
                                        <td/>
                                    </tr>
                                ))
                            }
                            </tbody>
                        </Table>

                        : <p>Enter date to see result</p>}
                </Container>

        </div>);
    }
}
export default SearchFlights;