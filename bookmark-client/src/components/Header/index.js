import "./header.css"
import { Link } from "react-router-dom";

function Header(){
    return(
        <header>
            <Link className="logo" to="/">Produtos Favoritos</Link>
            <Link className="favoritos" to="/favoritos">Meus Produtos</Link>
        </header>
    )
}

export default Header;