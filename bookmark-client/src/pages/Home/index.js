import {useEffect, useState} from "react";
import product_api from "../../services/product_api";
import {Link} from "react-router-dom";
import "./home.css"


function Home() {
    const [produtos, setProdutos] = useState([]);
    const[loading, setLoading] = useState(true);


    useEffect(() => {
        async function loadProdutos() {
            const response = await product_api.get("/products");
            setProdutos(response.data)
            setLoading(false);
        }
        loadProdutos();
    }, []);
    if(loading){
        return (
            <div className="loading">
                Carregando produtos ...
            </div>
        )
    }
    return (
        <div className="container">
            <div className="lista-filmes">
                {produtos.map((produto) => {
                    return (
                        <article key={produto.id}>
                            <strong>{produto.title}</strong>
                            <img src={`${produto.image}`}
                                 alt={produto.title}/>
                            <strong>R$ {produto.price}</strong>
                            <Link to={`/produto/${produto.id}`}>Acessar</Link>
                        </article>
                    )
                })}
            </div>
        </div>
    )
}

export default Home;