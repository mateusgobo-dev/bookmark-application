import "./header.css"
import { Link } from "react-router-dom";
import {useEffect, useState} from "react";

function Header(){
    const profile = localStorage.getItem("@profile");
    const [usuario, setUsuario] = useState({})

    useEffect(() => {
        let usuario = {};
        if(profile === null) {
            usuario = {
                id: 0,
                name: 'Usuário',
                email: 'usuario@usuario.com'
            }
        }else{
            usuario = JSON.parse(profile);
        }
        setUsuario(usuario);
    }, [profile]);

    return(
        <header>
            <Link className="logo" to="/">Produtos Favoritos</Link>
            {<label className="welcome">Bem vindo(a), {usuario.name}</label>}
            <Link className="favoritos" to="/favoritos">Meus Produtos</Link>
            <Link className="autenticar" to="/usuario">Acesso</Link>
        </header>
    )
}

export default Header;