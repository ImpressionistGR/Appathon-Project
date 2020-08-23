import React from 'react'
import ReactDOM from 'react-dom'
import axios from 'axios'
import qs from 'qs'

class FindArticle extends React.Component {
  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.state = {
        word: '',
        papers: []
    }
  }

  handleChange = e => {
    this.setState({word: e.target.value})
  }

  handleSubmit = event => {
    event.preventDefault();
    console.log(this.state)
    var str = this.state
    axios
         .patch('http://localhost:8080/covid/findArticleFromTitle', qs.stringify(str))
        .then(response => {
            console.log(response)
            this.setState((state) => {
                return {word: state.word, papers: response.data}
            })
        })
        .catch(error => {
            console.log(error)
        })
  }

   renderTableHeader() {
      return(
        <tr>
         <td>Title</td>
         <td>Publish_date</td>
         <td>url</td>
        </tr>
      )
   }

   renderTableData() {
      return this.state.papers.map((paper, index) => {
         const { sha, title, abstract, bodytext, journal, publish_time, url, status } = paper //destructuring
         return (
            <tr key={sha}>
               <td>{title}</td>
               <td>{publish_time}</td>
               <td>
                <a href={url}  target="_blank">
                 Watch Article
                </a>
               </td>
            </tr>
         )
      })
   }

  render() {
    return (
      <div>
        <h3>
          <br />
          Find Article From Title!
        </h3>
        <form onSubmit={this.handleSubmit}>
          <label>
            <input type="text" onChange={this.handleChange} value={this.state.word} />
          </label>
          <button type="submit">Find Article</button>
        </form>
        <table>
            <thead>
               {this.renderTableHeader()}
            </thead>
            <tbody>
               {this.renderTableData()}
            </tbody>
        </table>
      </div>
    );
  }
}

ReactDOM.render(
  <FindArticle />,
  document.getElementById('root')
);

export default FindArticle;