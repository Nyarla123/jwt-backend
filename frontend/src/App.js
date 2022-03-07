import React,{useState, useEffect} from "react";
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import Navbar from "./components/Navbar";
import {
    BrowserRouter as Router,
    Switch,
    Route,
} from 'react-router-dom';

function App() {

  const [message, setMessage] = useState("");
  const [users, setUsers] = useState([]);


  useEffect(() => {
    axios.get("/api/hello")
        .then((response) => {
          setMessage(response.data)
        });
  });

  return (
      <Router>
          <div className="App">
              <Navbar />
              <header className="App-header">
                  <img src={logo} className="App-logo" alt="logo" />
                  <p>
                      {message}
                  </p>
                  <a
                      className="App-link"
                      href="https://reactjs.org"
                      target="_blank"
                      rel="noopener noreferrer"
                  >
                      Learn React
                  </a>
              </header>
          </div>
      </Router>

  );
}

export default App;
