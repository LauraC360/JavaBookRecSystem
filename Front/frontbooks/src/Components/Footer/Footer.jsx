import "./Footer.css";

export const Footer = () =>
{
    return(
        <footer>
            <div className="footer-image"></div>
            <div className="footer-section">
                <div className="footer-content">
                    <p>Recommendations <br/>for my <br/>Books</p>
                </div>
                <div className="footer-contact">
                    <p>Contact</p>
                    <p id="colored">Chiriac Laura-Florina & Bindiu Ana-Maria - 2B3 -</p>
                    <p id="number"> Facultatea de Informatica Iasi</p>
                </div>
            </div>
        </footer>
    )
}