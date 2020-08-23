import {Bar} from 'react-chartjs-2'
import React from 'react'
import './App.css';
import ReactDOM from 'react-dom'
import axios from 'axios'
import qs from 'qs'

class BarChart extends React.Component {
  constructor(props) {
    super(props);
            this.state = {
                chartData: []
            }
  }

  handleClick = event => {
    event.preventDefault();
    console.log(this.state)
    axios
         .get('http://localhost:8080/covid/visualizeData')
        .then(response => {
            console.log(response)
            this.setState((state) => {
                return {chartData: response.data}
            })
        })
        .catch(error => {
            console.log(error)
        })
  }

    render() {
        return (
            <div className="chart">
                <button onClick={this.handleClick} >Visualize Data</button>
                <div className="main chart-wrapper">
                    <Bar
                        data={this.state.chartData[0]}
                        options={{maintainAspectRatio: false}}
                    />
                </div>
                <div className="sub chart-wrapper">
                    <Bar
                        data={this.state.chartData[1]}
                        options={{maintainAspectRatio: false}}
                    />
                </div>
                <div className="sub chart-wrapper">
                    <Bar
                        data={this.state.chartData[2]}
                        options={{maintainAspectRatio: false}}
                    />
                </div>
            </div>
        );
    }
}

ReactDOM.render(
  <BarChart />,
  document.getElementById('root')
);

export default BarChart;