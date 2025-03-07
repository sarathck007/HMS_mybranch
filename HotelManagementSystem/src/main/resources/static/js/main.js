// main.js - Main JavaScript for Hotel Reservation System

// Document Ready Function
document.addEventListener('DOMContentLoaded', function() {
    console.log('Hotel Reservation System initialized');
    
    // Initialize all tooltips
    initTooltips();
    
    // Date range validation for booking forms
    setupDateValidation();
    
    // Setup JWT token handling
    setupAuthTokenHandling();
    
    // Initialize any carousels
    initCarousels();
});

// Initialize Bootstrap tooltips
function initTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

// Setup date validation for booking forms
function setupDateValidation() {
    // Find check-in and check-out date inputs
    const checkInInput = document.getElementById('checkIn');
    const checkOutInput = document.getElementById('checkOut');
    
    if (checkInInput && checkOutInput) {
        // Set min date for check-in to today
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        const todayFormatted = `${year}-${month}-${day}`;
        
        checkInInput.min = todayFormatted;
        
        // When check-in date changes, update check-out minimum date
        checkInInput.addEventListener('change', function() {
            const checkInDate = new Date(this.value);
            const nextDay = new Date(checkInDate);
            nextDay.setDate(checkInDate.getDate() + 1);
            
            const nextYear = nextDay.getFullYear();
            const nextMonth = String(nextDay.getMonth() + 1).padStart(2, '0');
            const nextDayNum = String(nextDay.getDate()).padStart(2, '0');
            const nextDayFormatted = `${nextYear}-${nextMonth}-${nextDayNum}`;
            
            checkOutInput.min = nextDayFormatted;
            
            // If check-out is before new min date, update it
            if (new Date(checkOutInput.value) <= checkInDate) {
                checkOutInput.value = nextDayFormatted;
            }
        });
    }
}

// Setup JWT token handling
function setupAuthTokenHandling() {
    // Add Authorization header to all API requests if token exists
    fetch = (function(originalFetch) {
        return function(url, config) {
            // Get token from localStorage
            const token = localStorage.getItem('token');
            
            // Only add header for API requests
            if (url.includes('/api/') && token) {
                // Create config if it doesn't exist
                config = config || {};
                // Create headers if they don't exist
                config.headers = config.headers || {};
                // Add Authorization header with token
                config.headers['Authorization'] = 'Bearer ' + token;
            }
            
            return originalFetch(url, config);
        };
    })(fetch);
}

// Initialize carousels
function initCarousels() {
    const carouselElement = document.getElementById('heroCarousel');
    if (carouselElement) {
        const carousel = new bootstrap.Carousel(carouselElement, {
            interval: 5000,
            wrap: true
        });
    }
}

// Price calculator for booking form
function calculateTotalPrice(pricePerNight, nights) {
    const basePrice = pricePerNight * nights;
    const taxRate = 0.1; // 10% tax
    const taxAmount = basePrice * taxRate;
    const totalPrice = basePrice + taxAmount;
    
    // Update UI elements if they exist
    const basePriceElement = document.getElementById('basePrice');
    const taxElement = document.getElementById('taxAmount');
    const totalElement = document.getElementById('totalPrice');
    
    if (basePriceElement) basePriceElement.textContent = '$' + basePrice.toFixed(2);
    if (taxElement) taxElement.textContent = '$' + taxAmount.toFixed(2);
    if (totalElement) totalElement.textContent = '$' + totalPrice.toFixed(2);
    
    return {
        basePrice: basePrice.toFixed(2),
        taxAmount: taxAmount.toFixed(2),
        totalPrice: totalPrice.toFixed(2)
    };
}

// Handle booking form submission
function handleBookingSubmit(formId, apiEndpoint, redirectUrl) {
    const form = document.getElementById(formId);
    if (!form) return;
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Get form data
        const formData = new FormData(form);
        const bookingData = {};
        formData.forEach((value, key) => bookingData[key] = value);
        
        // Send request
        fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(bookingData)
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                // Show success message and redirect
                alert('Booking confirmed successfully!');
                window.location.href = redirectUrl || '/user/bookings';
            } else {
                // Show error message
                alert(result.message || 'Failed to complete booking. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while processing your booking. Please try again later.');
        });
    });
}