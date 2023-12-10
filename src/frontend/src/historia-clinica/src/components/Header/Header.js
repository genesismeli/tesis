import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserAvatar from './Avatar';
import './styles.css';
function Header() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');

  useEffect(() => {
    // Obtener el token almacenado en localStorage
    const token = localStorage.getItem('token');

    if (token) {
      // Realizar la solicitud para obtener el nombre de usuario
      fetch('http://localhost:8081/user/username', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      .then(response => response.text())
      .then(data => {
        setUsername(data);
      })
      .catch(error => {
        console.error('Error al obtener el nombre de usuario:', error);
      });
    }
  }, []);

     const handleLogout = () => {
        // Eliminar el token del almacenamiento local al cerrar sesión
        localStorage.removeItem('token');
        // Redirigir a la página de inicio de sesión
        setUsername(''); // Establecer el estado a un valor diferente para forzar la actualización
        navigate('');
      };
  //const handleGoBack = () => {
  //navigate(-1); // Navegar hacia atrás en la historia
  //};

  return (
    <div className="header">
      <div className="header-sticky">
          <h1 className='title'> HISTORIA CLÍNICA </h1>
        <div className="contain-left">
          {username && (
            <div className="user-info">
             <button className='cerrar-session' onClick={handleLogout}>Cerrar Sesión</button>
            <span className="welcome-message">¡Bienvenido {username}!  </span>
            <UserAvatar username={username} />
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Header;
