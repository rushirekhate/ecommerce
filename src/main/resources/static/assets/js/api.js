const API_BASE = 'http://localhost:8080/api';

// Token get karo
function getToken() {
    return localStorage.getItem('token') || 
           sessionStorage.getItem('token');
}

// User logged in hai?
function isLoggedIn() {
    return getToken() !== null;
}

// Logout
function logout() {
    localStorage.clear();
    sessionStorage.clear();
    window.location.href = '/login.html';
}

// Auth headers
function authHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + getToken()
    };
}

// Protected API call
async function apiCall(url, method = 'GET', body = null) {
    const options = {
        method,
        headers: authHeaders()
    };
    if (body) options.body = JSON.stringify(body);
    
    const response = await fetch(API_BASE + url, options);
    
    // 401 aaye toh logout
    if (response.status === 401) {
        logout();
        return null;
    }
    
    return response.json();
}

// Public API call (token nahi chahiye)
async function publicApiCall(url) {
    const response = await fetch(API_BASE + url);
    return response.json();
}