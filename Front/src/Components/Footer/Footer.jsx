import "./Footer.css";

export const Footer = () =>
{
    return(
        <footer>
            <div className="footer-image"></div>
            <div className="footer-section">
                <div className="footer-content">
                    <p>Front <br/>Books <br/></p>
                </div>
                <div className="footer-contact">
                   
                    <p id="colored">Bindiu Ana-Mara and Chiriac Laura-Florina</p>
                    <p id="number">Faculty of Computer Science Iasi</p>
                    <a><img src="img/ico/facebook.png" alt="facebook" /></a>
                    <a><img src="img/ico/twitter.png" alt="twitter" /></a>
                    <a><img src="img/ico/youtube.png" alt="youtube" /></a>
                    <a><img src="img/ico/instagram.png" alt="instagram" /></a>
                </div>
            </div>
        </footer>
    )
}