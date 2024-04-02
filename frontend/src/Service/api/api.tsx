import axios from "axios";

export const API = axios.create({
    baseURL: process.env.API_URL,
    timeout: 3000,
    timeoutErrorMessage: "Connection timeout",
    validateStatus: () => true
})

export type apiError = {
    errorData: string,
    status: number
}