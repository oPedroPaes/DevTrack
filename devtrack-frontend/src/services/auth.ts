import {apiFetch} from "./api.ts";

type LoginResponse = {
    token: string;
};

export function saveToken(token: string) {
    localStorage.setItem("token", token);
}

export function getToken() {
    return localStorage.getItem("token");
}

export async function login(email: string, password: string): Promise<LoginResponse> {
    const response = await apiFetch("auth/login", {
       method: "POST",
       body: JSON.stringify({email, password}),
       headers: {
           "Content-Type": "application/json",
       },
    });

    if (!response.ok) throw new Error("Erro no login");

    const data: LoginResponse = await response.json();

    if (!data.token) throw new Error("Token não recebido");

    return data;
}