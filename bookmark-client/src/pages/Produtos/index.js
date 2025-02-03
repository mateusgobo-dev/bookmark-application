import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import product_api from "../../services/product_api";
import bookmark_api from "../../services/bookmark_api"
import {Link} from "react-router-dom";
import "./produto-info.css"
import {toast} from "react-toastify";

function Produto() {
    const {id} = useParams();
    const [produto, setProduto] = useState({});
    const [loading, setLoading] = useState(true);
    const profile = localStorage.getItem("@profile");

    if(profile === null){
        toast.warn("Realize seu cadastro ou efetue o login para dar início a sua lista de favoritos...")
        setTimeout(redirectToUser, 3000);
    }
    useEffect(() => {
        async function loadProdutos() {
            const response = await product_api.get(`/products/${id}`)
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

    function redirectToUser(){
        window.location.href = '/usuario';
    }

    function salvarProduto(){
        const minhaLista = localStorage.getItem("@produtos");
        let produtosSalvos = JSON.parse(minhaLista) || [];

        let usuario = JSON.parse(profile);
        console.log(usuario)
        const bookmarkProduct = {
            idProduct: produto.id,
            idCustomer: usuario.id,
            email: usuario.email,
            add: true
        }

        if(produtosSalvos.length <= 4) {
            const hasProduto = produtosSalvos.some(p => p.id === produto.id);
            if (hasProduto) {
                toast.warn('Produto já faz parte da lista');
                return;
            }
            produtosSalvos.push(produto)
            localStorage.setItem("@produtos", JSON.stringify(produtosSalvos));

            async function addBookmark() {
                await bookmark_api.post("/api/v1/producer", bookmarkProduct).then(
                    response => {
                        toast.success(response.data)
                    }
                ).catch(() => {
                    toast.error("Falha ao adicionar favorito.")
                });
            }

            addBookmark();
        }else{
            toast.warn("Sua lista de favoritos é limitada à 5 produtos...");
        }
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
            <div className="produto-info">
                <h1>{produto.title}</h1>
                <img src={`${produto.image}`}
                     alt={produto.title}/>
                <h3>Descrição</h3>
                <span>{produto.description}</span>
                <strong>Avaliação: {produto.rating.rate} / 10</strong>

                <div className="area-buttons">
                    <button onClick={salvarProduto}>Salvar</button>
                </div>

                <Link to="/" className="voltar">Voltar</Link>
            </div>
        </div>
    )
}

export default Produto;