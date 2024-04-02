// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
  apiKey: "AIzaSyAJ-u02n0R7Rz_0sK45zNg3LbLM4JE65Jw",
  authDomain: "eletroficina-galvao.firebaseapp.com",
  projectId: "eletroficina-galvao",
  storageBucket: "eletroficina-galvao.appspot.com",
  messagingSenderId: "601631816395",
  appId: "1:601631816395:web:1ef3eaf8dddfc88790394e",
  measurementId: "G-HCKGHX7XJP"
};

const app = initializeApp(firebaseConfig);
export const storage = getStorage(app)
