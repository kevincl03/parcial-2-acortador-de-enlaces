import { useState } from 'react';
import { shortenLink } from '../services/api';

const URL_REGEX = /^(https?|ftp):\/\/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]/;

function Home() {
  const [formData, setFormData] = useState({
    enlace_original: '',
    url_imagen: '',
    descripcion: ''
  });

  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [result, setResult] = useState(null);

  const validate = () => {
    const newErrors = {};
    if (!formData.enlace_original.trim()) {
      newErrors.enlace_original = 'El enlace original es obligatorio';
    } else if (!URL_REGEX.test(formData.enlace_original)) {
      newErrors.enlace_original = 'La URL ingresada no es válida';
    }

    if (!formData.url_imagen.trim()) {
      newErrors.url_imagen = 'La URL de la imagen es obligatoria';
    } else if (!URL_REGEX.test(formData.url_imagen)) {
      newErrors.url_imagen = 'La URL de la imagen no es válida';
    }

    if (!formData.descripcion.trim()) {
      newErrors.descripcion = 'La descripción es obligatoria';
    } else {
      const words = formData.descripcion.trim().split(/\s+/).length;
      if (words < 5) {
        newErrors.descripcion = 'La descripción debe tener al menos 5 palabras';
      }
      if (formData.descripcion.length > 500) {
        newErrors.descripcion = 'La descripción no puede exceder 500 caracteres';
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setResult(null);
    
    if (!validate()) return;

    setIsLoading(true);
    try {
      const data = await shortenLink(formData);
      setResult(data);
      setFormData({ enlace_original: '', url_imagen: '', descripcion: '' });
    } catch (err) {
      alert(err.message || 'Ocurrió un error en el servidor');
    } finally {
      setIsLoading(false);
    }
  };

  const copyToClipboard = () => {
    if (result) {
      const urlToCopy = `http://localhost:8080/${result.enlace_acortado}`;
      navigator.clipboard.writeText(urlToCopy);
      alert('¡Enlace copiado al portapapeles!');
    }
  };

  return (
    <div>
      <h2>Acortador de Enlaces</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Enlace original</label>
          <input 
            type="text" 
            name="enlace_original"
            placeholder="https://www.ejemplo.com/largo"
            value={formData.enlace_original}
            onChange={handleChange}
          />
          {errors.enlace_original && <span className="error-text">{errors.enlace_original}</span>}
        </div>

        <div className="form-group">
          <label>URL de imagen</label>
          <input 
            type="text" 
            name="url_imagen"
            placeholder="https://www.ejemplo.com/imagen.jpg"
            value={formData.url_imagen}
            onChange={handleChange}
          />
          {errors.url_imagen && <span className="error-text">{errors.url_imagen}</span>}
        </div>

        <div className="form-group">
          <label>Descripción</label>
          <textarea 
            name="descripcion"
            rows="4"
            placeholder="Escribe al menos 5 palabras..."
            value={formData.descripcion}
            onChange={handleChange}
          />
          {errors.descripcion && <span className="error-text">{errors.descripcion}</span>}
        </div>

        <button type="submit" disabled={isLoading}>
          {isLoading ? 'ACORTANDO...' : 'ACORTAR'}
        </button>
      </form>

      {result && (
        <div className="success-message">
          <h3>¡Enlace acortado con éxito!</h3>
          <p>
            Tu enlace corto es: <br/>
            <a href={`http://localhost:8080/${result.enlace_acortado}`} target="_blank" rel="noreferrer">
              http://localhost:8080/{result.enlace_acortado}
            </a>
          </p>
          <button style={{marginTop: '1rem', background: '#28a745'}} onClick={copyToClipboard}>
            Copiar Enlace
          </button>
        </div>
      )}
    </div>
  );
}

export default Home;
