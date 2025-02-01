import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {toast} from "react-toastify";
import {bookmark} from "../../services/api"
import "./favoritos.css"


function Favoritos() {
    const [produtos, setProdutos] = useState([]);
    const [loading, setLoadiing] = useState(true);


    useEffect(() => {
        setLoadiing(false);
        setProdutos(JSON.parse(localStorage.getItem("@produtos")) || []);

    }, []);

    function removerProduto(produto) {
        let filtroProdutos = produtos.filter(p => {
            return p.id !== produto.id
        })
        setProdutos(filtroProdutos);
        localStorage.setItem("@produtos", JSON.stringify(filtroProdutos));

        let bookmarkProduct = {
            idProduct: produto.id,
            idCustomer: 1,
            email: "mateusgobo@gmail.com",
            add: false
        }

        async function removeBookmark(){
            await  bookmark.post("/api/v1/producer", bookmarkProduct).then(
                response => {
                    toast.success(response.data)
                }
            ).catch(()=>{
                toast.error("Falha ao remover favorito.")
            });
        }
        removeBookmark();
    }

    if (loading) {
        return (
            <div className="loading">
                Carregando produtos ...
            </div>
        )
    }
    return (
        <div className="container">
            {produtos.length === 0 &&
                <div>
                    <span>Você não possui produtos na área dos favoritos...</span><br />
                    <Link to="/">Voltar</Link>
                </div>
            }
            {produtos.map((produto) => {
                return (
                    <div className="favorito">
                        <h1>Meus produtos</h1>

                        <h1>{produto.title}</h1>
                        <img src={`${produto.image}`}
                             alt={produto.title}/>
                        <h3>Descrição</h3>
                        <span>{produto.description}</span>
                        <strong>Avaliação: {produto.rating.rate} / 10</strong>
                        <Link to={`/produto/${produto.id}`}>Detalhes</Link>
                        <div className="area-buttons">
                            <button onClick={() => removerProduto(produto)}>Remover</button>
                        </div>
                    </div>
                )
            })}
        </div>
    )
}

export default Favoritos;