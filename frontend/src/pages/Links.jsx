import { useEffect, useState } from 'react';
import { getLinks } from '../services/api';

function Links() {
  const [links, setLinks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchLinks();
  }, []);

  const fetchLinks = async () => {
    try {
      const data = await getLinks();
      setLinks(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading) return <p>Cargando enlaces...</p>;
  if (error) return <p className="error-text">Error: {error}</p>;

  return (
    <div>
      <h2>Mis Enlaces Registrados</h2>
      
      {links.length === 0 ? (
        <p>No hay enlaces registrados aún.</p>
      ) : (
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Imagen</th>
                <th>Enlace original</th>
                <th>Descripción</th>
                <th>Enlace acortado</th>
              </tr>
            </thead>
            <tbody>
              {links.map((link, index) => (
                <tr key={index}>
                  <td>
                    {link.url_imagen && (
                      <img src={link.url_imagen} alt="Miniatura" className="thumb" />
                    )}
                  </td>
                  <td>
                    <div className="truncate" title={link.enlace_original}>
                      <a href={link.enlace_original} target="_blank" rel="noreferrer">
                        {link.enlace_original}
                      </a>
                    </div>
                  </td>
                  <td>
                    <div className="truncate" title={link.descripcion}>
                      {link.descripcion}
                    </div>
                  </td>
                  <td>
                    <a href={`http://localhost:8080/${link.enlace_acortado.replace(/^\/+/, '')}`} target="_blank" rel="noreferrer">
                      /{link.enlace_acortado.replace(/^\/+/, '')}
                    </a>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default Links;
