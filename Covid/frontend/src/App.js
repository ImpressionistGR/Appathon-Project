import React from 'react';
import logo from './logo.svg';
import './App.css';
import ReactDOM from 'react-dom'
import FileInput from './FileInput'
import FindArticle from './FindArticle'
import BarChart from './BarChart'

class App extends React.Component {

    constructor(props) {
        super(props)
    }

    render() {
      return (
        <div className="App">
            <FileInput />
            <BarChart />
            <FindArticle />
        </div>

      );
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('root')
);

export default App;
