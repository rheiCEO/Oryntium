// ========================================
// ORYNTIUM - JavaScript Interactions
// ========================================

// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Parallax effect on scroll
let ticking = false;

window.addEventListener('scroll', () => {
    if (!ticking) {
        window.requestAnimationFrame(() => {
            handleParallax();
            handleScrollAnimations();
            ticking = false;
        });
        ticking = true;
    }
});

function handleParallax() {
    const scrolled = window.pageYOffset;
    const heroVisual = document.querySelector('.hero-visual');
    
    if (heroVisual) {
        heroVisual.style.transform = `translateY(${scrolled * 0.3}px)`;
    }
}

// Intersection Observer for scroll animations
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, observerOptions);

function handleScrollAnimations() {
    // Animate cards on scroll
    const cards = document.querySelectorAll('.feature-card, .benefit-card, .step');
    cards.forEach(card => {
        if (!card.style.opacity) {
            card.style.opacity = '0';
            card.style.transform = 'translateY(50px)';
            card.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
        }
        observer.observe(card);
    });
}

// Initialize scroll animations on page load
document.addEventListener('DOMContentLoaded', () => {
    handleScrollAnimations();
    initializeGlitchEffect();
    initializeNavbarScroll();
    initializeCursorEffect();
    initializeParticles();
    initializeMobileMenu();
});

// Glitch effect for title
function initializeGlitchEffect() {
    const glitchElements = document.querySelectorAll('.glitch');
    
    glitchElements.forEach(element => {
        setInterval(() => {
            if (Math.random() > 0.9) {
                element.style.textShadow = `
                    ${Math.random() * 10 - 5}px ${Math.random() * 10 - 5}px 10px var(--neon-blue),
                    ${Math.random() * 10 - 5}px ${Math.random() * 10 - 5}px 20px var(--neon-pink)
                `;
                
                setTimeout(() => {
                    element.style.textShadow = '';
                }, 50);
            }
        }, 100);
    });
}

// Navbar background on scroll
function initializeNavbarScroll() {
    const nav = document.querySelector('.nav');
    
    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            nav.style.background = 'rgba(10, 10, 10, 0.95)';
            nav.style.backdropFilter = 'blur(20px)';
        } else {
            nav.style.background = 'rgba(10, 10, 10, 0.8)';
            nav.style.backdropFilter = 'blur(10px)';
        }
    });
}

// Custom cursor effect
function initializeCursorEffect() {
    const cursor = document.createElement('div');
    cursor.className = 'custom-cursor';
    document.body.appendChild(cursor);
    
    document.addEventListener('mousemove', (e) => {
        cursor.style.left = e.clientX + 'px';
        cursor.style.top = e.clientY + 'px';
    });
    
    // Cursor hover effects
    const hoverElements = document.querySelectorAll('a, button, .feature-card, .benefit-card');
    hoverElements.forEach(element => {
        element.addEventListener('mouseenter', () => {
            cursor.style.transform = 'scale(1.5)';
            cursor.style.background = 'rgba(187, 0, 255, 0.3)';
        });
        
        element.addEventListener('mouseleave', () => {
            cursor.style.transform = 'scale(1)';
            cursor.style.background = 'rgba(0, 240, 255, 0.2)';
        });
    });
}

// Floating particles
function initializeParticles() {
    const particleContainer = document.createElement('div');
    particleContainer.className = 'particles';
    document.body.appendChild(particleContainer);
    
    for (let i = 0; i < 50; i++) {
        createParticle(particleContainer);
    }
}

function createParticle(container) {
    const particle = document.createElement('div');
    particle.className = 'particle';
    
    const size = Math.random() * 3 + 1;
    const x = Math.random() * window.innerWidth;
    const y = Math.random() * window.innerHeight;
    const duration = Math.random() * 20 + 10;
    
    particle.style.width = size + 'px';
    particle.style.height = size + 'px';
    particle.style.left = x + 'px';
    particle.style.top = y + 'px';
    particle.style.animationDuration = duration + 's';
    
    container.appendChild(particle);
}

// Konami Code Easter Egg
let konamiCode = [];
const konamiSequence = [
    'ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown',
    'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight',
    'KeyB', 'KeyA'
];

document.addEventListener('keydown', (e) => {
    konamiCode.push(e.code);
    
    if (konamiCode.length > konamiSequence.length) {
        konamiCode.shift();
    }
    
    if (konamiCode.join(',') === konamiSequence.join(',')) {
        activateMatrixMode();
        konamiCode = [];
    }
});

function activateMatrixMode() {
    document.body.classList.add('matrix-mode');
    
    // Add matrix rain effect
    const matrixContainer = document.createElement('div');
    matrixContainer.className = 'matrix-rain';
    document.body.appendChild(matrixContainer);
    
    // Show easter egg message
    const easterEgg = document.createElement('div');
    easterEgg.className = 'easter-egg';
    easterEgg.innerHTML = `
        <div class="easter-egg-content">
            <h3>🎉 KONAMI CODE ACTIVATED!</h3>
            <p>Welcome to the Matrix, Neo!</p>
            <p>ORYNTIUM Matrix Mode enabled</p>
            <button onclick="deactivateMatrixMode()">Exit Matrix</button>
        </div>
    `;
    document.body.appendChild(easterEgg);
    
    setTimeout(() => {
        easterEgg.classList.add('show');
    }, 100);
    
    // Matrix rain animation
    createMatrixRain();
}

function deactivateMatrixMode() {
    document.body.classList.remove('matrix-mode');
    const matrixRain = document.querySelector('.matrix-rain');
    const easterEgg = document.querySelector('.easter-egg');
    
    if (matrixRain) matrixRain.remove();
    if (easterEgg) easterEgg.remove();
}

function createMatrixRain() {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');
    canvas.style.position = 'fixed';
    canvas.style.top = '0';
    canvas.style.left = '0';
    canvas.style.width = '100%';
    canvas.style.height = '100%';
    canvas.style.pointerEvents = 'none';
    canvas.style.zIndex = '9999';
    canvas.style.opacity = '0.3';
    
    const matrixContainer = document.querySelector('.matrix-rain');
    matrixContainer.appendChild(canvas);
    
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    const characters = '01アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン';
    const fontSize = 14;
    const columns = canvas.width / fontSize;
    const drops = [];
    
    for (let i = 0; i < columns; i++) {
        drops[i] = 1;
    }
    
    function drawMatrix() {
        ctx.fillStyle = 'rgba(0, 0, 0, 0.05)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        
        ctx.fillStyle = '#00FF41';
        ctx.font = fontSize + 'px monospace';
        
        for (let i = 0; i < drops.length; i++) {
            const text = characters[Math.floor(Math.random() * characters.length)];
            ctx.fillText(text, i * fontSize, drops[i] * fontSize);
            
            if (drops[i] * fontSize > canvas.height && Math.random() > 0.975) {
                drops[i] = 0;
            }
            drops[i]++;
        }
    }
    
    setInterval(drawMatrix, 35);
}

// Performance optimization
window.addEventListener('resize', () => {
    const particles = document.querySelectorAll('.particle');
    particles.forEach(particle => {
        const newX = Math.random() * window.innerWidth;
        const newY = Math.random() * window.innerHeight;
        particle.style.left = newX + 'px';
        particle.style.top = newY + 'px';
    });
});

// Preload critical resources
function preloadResources() {
    const criticalImages = [
        'favicon.svg'
    ];
    
    criticalImages.forEach(src => {
        const link = document.createElement('link');
        link.rel = 'preload';
        link.as = 'image';
        link.href = src;
        document.head.appendChild(link);
    });
}

// Mobile menu functionality
function initializeMobileMenu() {
    const menuToggle = document.querySelector('.menu-toggle');
    const navigation = document.querySelector('.main-navigation');
    
    if (menuToggle && navigation) {
        menuToggle.addEventListener('click', () => {
            navigation.classList.toggle('active');
            menuToggle.classList.toggle('active');
            
            // Update aria-expanded
            const isExpanded = navigation.classList.contains('active');
            menuToggle.setAttribute('aria-expanded', isExpanded);
        });
        
        // Close menu when clicking outside
        document.addEventListener('click', (e) => {
            if (!navigation.contains(e.target) && !menuToggle.contains(e.target)) {
                navigation.classList.remove('active');
                menuToggle.classList.remove('active');
                menuToggle.setAttribute('aria-expanded', 'false');
            }
        });
        
        // Close menu on window resize
        window.addEventListener('resize', () => {
            if (window.innerWidth > 768) {
                navigation.classList.remove('active');
                menuToggle.classList.remove('active');
                menuToggle.setAttribute('aria-expanded', 'false');
            }
        });
    }
}

// Language switcher functionality
function toggleLanguages() {
    const dropdown = document.getElementById('languageDropdown');
    if (dropdown) {
        dropdown.classList.toggle('show');
    }
}

function changeLanguage(lang) {
    // Store selected language in localStorage
    localStorage.setItem('oryntium_language', lang);
    
    // Update the button text with flag
    const button = document.querySelector('.language-toggle');
    const flagMap = {
        'pl_PL': '🇵🇱',
        'en_US': '🇺🇸', 
        'es_ES': '🇪🇸',
        'de_DE': '🇩🇪',
        'hi_IN': '🇮🇳',
        'ar': '🇸🇦',
        'fr_FR': '🇫🇷',
        'zh_CN': '🇨🇳'
    };
    
    if (button) {
        const langCode = lang.split('_')[0].toUpperCase();
        button.innerHTML = `
            <svg viewBox="0 0 24 24" width="20" height="20">
                <path fill="currentColor" d="M9,4V7H10.21L8.5,13.5V17H15.5V13.5L13.79,7H15V4H9M12,8.91L13.5,11H10.5L12,8.91Z"/>
            </svg>
            ${flagMap[lang]} ${langCode}
        `;
    }
    
    // Close dropdown
    const dropdown = document.getElementById('languageDropdown');
    if (dropdown) {
        dropdown.classList.remove('show');
    }
    
    // In real WordPress, this would reload the page with new language
    // For demo purposes, we'll just show an alert
    alert(`Język zmieniony na: ${lang}\n\nW rzeczywistej aplikacji WordPress:\n1. Zainstaluj plugin Polylang\n2. Skonfiguruj języki\n3. Przełącznik będzie działał automatycznie!`);
}

// Close language dropdown when clicking outside
document.addEventListener('click', (e) => {
    const dropdown = document.getElementById('languageDropdown');
    const toggle = document.querySelector('.language-toggle');

    if (dropdown && toggle && !toggle.contains(e.target) && !dropdown.contains(e.target)) {
        dropdown.classList.remove('show');
    }
});

// Initialize language on page load
document.addEventListener('DOMContentLoaded', function() {
    const savedLang = localStorage.getItem('oryntium_language');
    if (savedLang) {
        // Update button to show saved language
        const button = document.querySelector('.language-toggle');
        if (button) {
            const flagMap = {
                'pl_PL': '🇵🇱',
                'en_US': '🇺🇸', 
                'es_ES': '🇪🇸',
                'de_DE': '🇩🇪',
                'hi_IN': '🇮🇳',
                'ar': '🇸🇦',
                'fr_FR': '🇫🇷',
                'zh_CN': '🇨🇳'
            };
            const langCode = savedLang.split('_')[0].toUpperCase();
            button.innerHTML = `
                <svg viewBox="0 0 24 24" width="20" height="20">
                    <path fill="currentColor" d="M9,4V7H10.21L8.5,13.5V17H15.5V13.5L13.79,7H15V4H9M12,8.91L13.5,11H10.5L12,8.91Z"/>
                </svg>
                ${flagMap[savedLang]} ${langCode}
            `;
        }
    }
});

// Initialize everything when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', preloadResources);
} else {
    preloadResources();
}
