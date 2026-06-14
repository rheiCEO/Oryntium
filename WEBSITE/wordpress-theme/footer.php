<?php
/**
 * The template for displaying the footer
 *
 * Contains the closing of the #content div and all content after.
 *
 * @package ORYNTIUM
 * @since 1.0.0
 */
?>

    </div><!-- #content -->

    <footer id="colophon" class="site-footer">
        <div class="container">
            <div class="footer-content">
                <div class="footer-logo">
                    <svg class="footer-logo-icon" viewBox="0 0 100 100" width="60" height="60">
                        <defs>
                            <linearGradient id="footerLogoGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                                <stop offset="0%" style="stop-color:#BB00FF;stop-opacity:1" />
                                <stop offset="100%" style="stop-color:#00F0FF;stop-opacity:1" />
                            </linearGradient>
                        </defs>
                        <rect x="30" y="30" width="40" height="40" fill="none" stroke="url(#footerLogoGradient)" stroke-width="3" transform="rotate(45 50 50)"/>
                        <rect x="35" y="35" width="30" height="30" fill="none" stroke="url(#footerLogoGradient)" stroke-width="2" transform="rotate(45 50 50)"/>
                    </svg>
                    <div>
                        <div class="footer-brand"><?php echo get_bloginfo('name'); ?></div>
                        <div class="footer-tagline"><?php echo __('Keep it yours', 'oryntium'); ?></div>
                    </div>
                </div>
                
                <div class="footer-info">
                    <p class="footer-text">
                        <?php echo __('Najnowocześniejsza platforma szyfrowania SMS w Polsce.', 'oryntium'); ?>
                        <br>
                        <?php echo __('Twoje wiadomości, Twoja prywatność, Twoja kontrola.', 'oryntium'); ?>
                    </p>
                </div>
                
                <div class="footer-bottom">
                    <div class="footer-copyright">
                        © <?php echo date('Y'); ?> <?php echo get_bloginfo('name'); ?>. <?php echo __('Wszystkie prawa zastrzeżone.', 'oryntium'); ?>
                    </div>
                    <div class="footer-credits">
                        <?php echo __('Powered by', 'oryntium'); ?> <span class="highlight">rhei</span>
                    </div>
                </div>
            </div>
        </div>
    </footer>

</div><!-- #page -->

<?php wp_footer(); ?>

<script>
// ========================================
// ORYNTIUM - WordPress JavaScript
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
            handleHeaderScroll();
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

function handleHeaderScroll() {
    const header = document.querySelector('.site-header');
    
    if (header) {
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
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

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    handleScrollAnimations();
    initializeGlitchEffect();
    initializeMobileMenu();
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

// Mobile menu functionality
function initializeMobileMenu() {
    const menuToggle = document.querySelector('.menu-toggle');
    const navigation = document.querySelector('.main-navigation');
    
    if (menuToggle && navigation) {
        menuToggle.addEventListener('click', () => {
            const isExpanded = menuToggle.getAttribute('aria-expanded') === 'true';
            menuToggle.setAttribute('aria-expanded', !isExpanded);
            navigation.classList.toggle('active');
            document.body.classList.toggle('menu-open');
        });
    }
}

// Custom cursor effect
function initializeCursorEffect() {
    // Only on desktop
    if (window.innerWidth < 768) return;
    
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
    
    if (overlay) {
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
}

randomGlitchEffect();

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

console.log('%c<?php echo __('Jeśli widzisz to, jesteś ciekawy!', 'oryntium'); ?> 🔍', 'color: #00F0FF; font-size: 16px; font-weight: bold;');
console.log('%c<?php echo __('Spróbuj wpisać kod Konami!', 'oryntium'); ?> ⬆️⬆️⬇️⬇️⬅️➡️⬅️➡️BA', 'color: #00FF41; font-size: 14px;');
</script>

</body>
</html>


