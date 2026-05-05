const API_URL = 'http://localhost:8080/api/links';

export const shortenLink = async (payload) => {
   const response = await fetch(`${API_URL}/shorten`, {
      method: 'POST',
      headers: {
         'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload)
   });

   if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.error || 'Error al acortar el enlace');
   }

   return response.json();
};

export const getLinks = async () => {
   const response = await fetch(API_URL);
   if (!response.ok) {
      throw new Error('Error al obtener los enlaces');
   }
   return response.json();
};