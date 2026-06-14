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
            nav.style.boxShadow = '0 5px 30px rgba(187, 0, 255, 0.3)';
        } else {
            nav.style.background = 'rgba(10, 10, 10, 0.8)';
            nav.style.boxShadow = 'none';
        }
    });
}

// Custom cursor effect
function initializeCursorEffect() {
    const cursor = document.createElement('div');
    cursor.classList.add('custom-cursor');
    document.body.appendChild(cursor);
    
    const cursorStyle = document.createElement('style');
    cursorStyle.textContent = `
        .custom-cursor {
            width: 20px;
            height: 20px;
            border: 2px solid var(--neon-purple);
            border-radius: 50%;
            position: fixed;
            pointer-events: none;
            z-index: 10000;
            transition: transform 0.2s ease, border-color 0.2s ease;
            box-shadow: 0 0 20px var(--neon-purple);
            mix-blend-mode: difference;
        }
        
        .custom-cursor.hover {
            transform: scale(1.5);
            border-color: var(--neon-cyan);
            box-shadow: 0 0 30px var(--neon-cyan);
        }
    `;
    document.head.appendChild(cursorStyle);
    
    document.addEventListener('mousemove', (e) => {
        cursor.style.left = e.clientX - 10 + 'px';
        cursor.style.top = e.clientY - 10 + 'px';
    });
    
    // Add hover effect to interactive elements
    const interactiveElements = document.querySelectorAll('a, button, .btn, .feature-card, .benefit-card');
    
    interactiveElements.forEach(element => {
        element.addEventListener('mouseenter', () => {
            cursor.classList.add('hover');
        });
        
        element.addEventListener('mouseleave', () => {
            cursor.classList.remove('hover');
        });
    });
}

// Add random glitch effect to overlay
function randomGlitchEffect() {
    const overlay = document.querySelector('.glitch-overlay');
    
    setInterval(() => {
        if (Math.random() > 0.95) {
            overlay.style.opacity = '0.6';
            overlay.style.transform = `translate(${Math.random() * 10 - 5}px, ${Math.random() * 10 - 5}px)`;
            
            setTimeout(() => {
                overlay.style.opacity = '0.3';
                overlay.style.transform = 'translate(0, 0)';
            }, 100);
        }
    }, 200);
}

randomGlitchEffect();

// Add typing effect to hero subtitle
function typeWriter(element, text, speed = 100) {
    let i = 0;
    element.textContent = '';
    
    function type() {
        if (i < text.length) {
            element.textContent += text.charAt(i);
            i++;
            setTimeout(type, speed);
        }
    }
    
    type();
}

// Easter egg: Konami code
let konamiCode = [];
const konamiSequence = ['ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight', 'b', 'a'];

document.addEventListener('keydown', (e) => {
    konamiCode.push(e.key);
    konamiCode = konamiCode.slice(-10);
    
    if (konamiCode.join(',') === konamiSequence.join(',')) {
        activateMatrixMode();
    }
});

function activateMatrixMode() {
    const body = document.body;
    body.style.animation = 'matrix-rain 20s linear';
    
    const matrixStyle = document.createElement('style');
    matrixStyle.textContent = `
        @keyframes matrix-rain {
            0% { filter: hue-rotate(0deg); }
            100% { filter: hue-rotate(360deg); }
        }
    `;
    document.head.appendChild(matrixStyle);
    
    // Create matrix rain effect
    const canvas = document.createElement('canvas');
    canvas.style.position = 'fixed';
    canvas.style.top = '0';
    canvas.style.left = '0';
    canvas.style.width = '100%';
    canvas.style.height = '100%';
    canvas.style.pointerEvents = 'none';
    canvas.style.zIndex = '9998';
    document.body.appendChild(canvas);
    
    const ctx = canvas.getContext('2d');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    const chars = '01';
    const fontSize = 14;
    const columns = canvas.width / fontSize;
    const drops = Array(Math.floor(columns)).fill(1);
    
    function drawMatrix() {
        ctx.fillStyle = 'rgba(10, 10, 10, 0.05)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        
        ctx.fillStyle = '#00FF41';
        ctx.font = fontSize + 'px monospace';
        
        for (let i = 0; i < drops.length; i++) {
            const text = chars[Math.floor(Math.random() * chars.length)];
            ctx.fillText(text, i * fontSize, drops[i] * fontSize);
            
            if (drops[i] * fontSize > canvas.height && Math.random() > 0.975) {
                drops[i] = 0;
            }
            drops[i]++;
        }
    }
    
    const matrixInterval = setInterval(drawMatrix, 33);
    
    setTimeout(() => {
        clearInterval(matrixInterval);
        canvas.remove();
        matrixStyle.remove();
        body.style.animation = '';
    }, 20000);
}

// Add particle effect on hero section
function createParticles() {
    const hero = document.querySelector('.hero');
    const particleCount = 50;
    
    for (let i = 0; i < particleCount; i++) {
        const particle = document.createElement('div');
        particle.classList.add('particle');
        
        const size = Math.random() * 3 + 1;
        const x = Math.random() * 100;
        const duration = Math.random() * 20 + 10;
        const delay = Math.random() * 5;
        
        particle.style.cssText = `
            position: absolute;
            width: ${size}px;
            height: ${size}px;
            background: ${Math.random() > 0.5 ? 'var(--neon-purple)' : 'var(--neon-cyan)'};
            border-radius: 50%;
            left: ${x}%;
            top: 100%;
            animation: float-up ${duration}s ${delay}s infinite;
            opacity: 0.3;
            box-shadow: 0 0 10px currentColor;
            pointer-events: none;
        `;
        
        hero.appendChild(particle);
    }
    
    const particleStyle = document.createElement('style');
    particleStyle.textContent = `
        @keyframes float-up {
            0% {
                transform: translateY(0) translateX(0);
                opacity: 0;
            }
            10% {
                opacity: 0.3;
            }
            90% {
                opacity: 0.3;
            }
            100% {
                transform: translateY(-100vh) translateX(${Math.random() * 200 - 100}px);
                opacity: 0;
            }
        }
    `;
    document.head.appendChild(particleStyle);
}

createParticles();

// Performance optimization: Reduce animations on low-end devices
if (navigator.hardwareConcurrency && navigator.hardwareConcurrency < 4) {
    document.body.classList.add('reduce-animations');
    
    const reduceAnimStyle = document.createElement('style');
    reduceAnimStyle.textContent = `
        .reduce-animations * {
            animation-duration: 0.01s !important;
            transition-duration: 0.01s !important;
        }
    `;
    document.head.appendChild(reduceAnimStyle);
}

// Add loading animation
window.addEventListener('load', () => {
    document.body.style.opacity = '0';
    document.body.style.transition = 'opacity 0.5s ease';
    
    setTimeout(() => {
        document.body.style.opacity = '1';
    }, 100);
});

// Console easter egg
console.log(`
%c
    ╔═══════════════════════════════════════╗
    ║                                       ║
    ║          ORYNTIUM v1.0.0              ║
    ║       Secure Messaging Platform       ║
    ║                                       ║
    ║         Powered by rhei               ║
    ║         Keep it yours                 ║
    ║                                       ║
    ╚═══════════════════════════════════════╝

`, 'color: #BB00FF; font-family: monospace; font-size: 12px;');

console.log('%cJeśli widzisz to, jesteś ciekawy! 🔍', 'color: #00F0FF; font-size: 16px; font-weight: bold;');
console.log('%cSpróbuj wpisać kod Konami! ⬆️⬆️⬇️⬇️⬅️➡️⬅️➡️BA', 'color: #00FF41; font-size: 14px;');







