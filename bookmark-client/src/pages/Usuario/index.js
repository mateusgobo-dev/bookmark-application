import {React, useEffect, useState} from "react";
import './usuario-form.css'

function Usuario() {
    const userDto = {
        nome: "", endereco: "", email: "", senha: ""
    }
    // ;;nome: "", endereco: "", email: "", senha: ""
    const [usuario, setUsuario] = useState({userDto});
    useEffect(() => {
    }, []);

    const handleForm = (e) => {
        setUsuario({...usuario, [e.target.name]: e.target.value});
    }

    function salvar() {
        console.log(usuario);
    }

    return (
        <div className="usuario-form">
            <h1>Usuario</h1>
            <div>
                <label htmlFor="nome">Nome</label>
                <input type="text" id="nome" name="nome" value={usuario.nome} onChange={handleForm}/>
                <label htmlFor="endereco">Endereço</label>
                <input type="text" id="endereco" name="endereco" value={usuario.endereco} onChange={handleForm}/>
                <label htmlFor="email">Email</label>
                <input type="email" id="email" name="email" value={usuario.email} onChange={handleForm}/>
                <label htmlFor="senha">Senha</label>
                <input type="password" id="password" name="password" value={usuario.senha} onChange={handleForm}/>
                <span className="area-buttons">
                    <button onClick={() => salvar()}>Salvar</button>
                </span>
            </div>
        </div>)
}

export default Usuario