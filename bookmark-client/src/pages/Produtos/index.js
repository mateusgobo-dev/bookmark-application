import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import api from "../../services/api";
import {Link} from "react-router-dom";
import "./produto-info.css"

function Produto() {
    const {id} = useParams();
    const [produto, setProduto] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadProdutos() {
            const response = await api.get(`/products/${id}`)
                .then(response => {
                    setProduto(response.data)
                    setLoading(false);
                })
                .catch(() => {
                    console.log("Produto nao encontrado!");
                });
        }

        loadProdutos();
        return () => {
            console.log("Componente desmontado")
        }
    }, []);
    if (loading) {
        return (
            <div className="loading">
                Carregando produtos ...
            </div>
        )
    }
    return (
        <div className="container">
            <div className="produto-info">
                <h1>{produto.title}</h1>
                <img src={`${produto.image}`}
                     alt={produto.title}/>
                <h3>Descrição</h3>
                <span>{produto.description}</span>
                <strong>Avaliação: {produto.rating.rate} / 10</strong>

                <div className="area-buttons">
                    <button>Salvar</button>
                </div>

                <Link to="/" className="voltar">Voltar</Link>
            </div>
        </div>
    )
}

export default Produto;