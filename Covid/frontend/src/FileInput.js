import React from 'react'
import ReactDOM from 'react-dom'
import axios from 'axios'
import qs from 'qs'
import './FileInput.css'
import * as ReactBootstrap from "react-bootstrap"

class FileInput extends React.Component {
  constructor(props) {
    // highlight-range{3}
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.fileInput = React.createRef();
    this.state = {
        sha: '',
        response: ''
    }
  }
  handleSubmit = event => {
    // highlight-range{3}
    event.preventDefault();
    var str = this.fileInput.current.files[0].name
    this.state.sha = str.substring(0, str.lastIndexOf(".json"))
    console.log(this.state)
    axios
         .patch('http://localhost:8080/covid/update', qs.stringify(this.state))
        .then(response => {
            console.log(response)
            this.setState((state) => {
                return {sha: state.sha, response: response.data}
            })
        })
        .catch(error => {
            console.log(error)
        })
  }

  render() {
    // highlight-range{5}

    return (
      <div>
        <h1>
          <br />
          Upload your Article to Covid Database!
        </h1>
        <form onSubmit={this.handleSubmit}>
          <label>
            Upload file:
            <input type="file" ref={this.fileInput} />
          </label>
          <button type="submit">Submit</button>
          <label>{this.state.response}</label>
        </form>
      </div>
    );
  }
}

ReactDOM.render(
  <FileInput />,
  document.getElementById('root')
);

export default FileInput;