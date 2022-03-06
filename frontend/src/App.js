import React,{useState, useEffect} from "react";
import axios from "axios";
import {
    BrowserRouter as Router,
    Route,
    Switch
} from 'react-router-dom';
import User from "./components/User";
import Header from "./components/Header";

const config = {
    headers: {
        Authorization: "Bearer=" + localStorage.getItem("jwtToken"),
    },
};

function App() {

    const [user, setUser] = useState(null);
    console.log(config);
  return (
      <Router>
          <Header />
          <Route>
              <User path='/'/>
          </Route>
      </Router>
  );
}

export default App;
