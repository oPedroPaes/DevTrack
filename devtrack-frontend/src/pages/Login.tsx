import { useState, type FormEvent } from "react";
import {login, saveToken} from "../services/auth";

import Navbar from "../components/Navbar";
function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    async function handleLogin(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();

        try {
            const data = await login(email, password);
            saveToken(data.token);
        } catch (error) {
            setError("Email ou senha inválidos");
            console.error("Falha:", error)
        }
    }

    return (
        <div>
            <Navbar />
            <h1>Login</h1>

            <form onSubmit={handleLogin}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Senha"
                    value={password}
                    onChange={(e) => {
                        setPassword(e.target.value);
                        setError("");
                    }}
                />

                <button type="submit">Entrar</button>
            </form>
            {error && <p style={{ color: "red"}}>{error}</p>}
        </div>
    );
}

export default Login;