import {BrowserRouter, Routes, Route} from "react-router-dom";

import "./pages/Home"
import "./pages/Produtos"
import Home from "./pages/Home";
import Header from "./components/Header";
import Erro from "./pages/Erro";
import Produto from "./pages/Produtos";
import Favoritos from "./pages/Favoritos";
import Usuario from "./pages/Usuario";

function RoutesApp(){
    return (
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={<Home/>}></Route>
                <Route path="/produto/:id" element={<Produto/>}></Route>
                <Route path="/favoritos" element={<Favoritos/>}></Route>
                <Route path="/usuario" element={<Usuario />}></Route>
                <Route path="*" element={<Erro />}></Route>
            </Routes>
        </BrowserRouter>
    )
}

export default RoutesApp;