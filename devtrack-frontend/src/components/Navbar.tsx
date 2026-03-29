import React from "react";
import { Link } from "react-router-dom";

const Navbar: React.FC = () => {
  return (
    <nav>
      <ul style={{ display: "flex", gap: "1rem", listStyle: "none" }}>
        <li>
          <Link to="/">Login</Link>
        </li>
        <li>
          <Link to="/dashboard">Dashboard</Link>
        </li>
        <li>
          <Link to="/goals">Metas</Link>
        </li>
        <li>
          <Link to="/sessions">Sessões</Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
