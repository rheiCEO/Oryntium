// ========================================
// ORYNTIUM - PREMIUM EFFECTS
// Ultra cyberpunk interactive experience
// ========================================

document.addEventListener('DOMContentLoaded', function() {
    initLoadingScreen();
    initParticles();
    initMouseTrail();
    init3DTilt();
    initTypewriter();
    initAnimatedCounters();
    initParallaxEverything();
    initHolographicCards();
    initSmoothTransitions();
});

// ========================================
// LOADING SCREEN
// ========================================
function initLoadingScreen() {
    const loader = document.createElement('div');
    loader.className = 'premium-loader';
    loader.innerHTML = `
        <div class="loader-content">
            <svg class="loader-logo" viewBox="0 0 100 100" width="100" height="100">
                <defs>
                    <linearGradient id="loaderGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                        <stop offset="0%" style="stop-color:#BB00FF;stop-opacity:1" />
                        <stop offset="100%" style="stop-color:#00F0FF;stop-opacity:1" />
                    </linearGradient>
                </defs>
                <rect class="loader-diamond" x="30" y="30" width="40" height="40" fill="none" 
                      stroke="url(#loaderGradient)" stroke-width="3" transform="rotate(45 50 50)"/>
            </svg>
            <div class="loader-text">ORYNTIUM</div>
            <div class="loader-bar">
                <div class="loader-progress"></div>
            </div>
            <div class="loader-subtext">Keep it yours...</div>
        </div>
    `;
    document.body.appendChild(loader);
    
    let progress = 0;
    const progressBar = loader.querySelector('.loader-progress');
    const interval = setInterval(() => {
        progress += Math.random() * 30;
        if (progress >= 100) {
            progress = 100;
            clearInterval(interval);
            setTimeout(() => {
                loader.classList.add('loaded');
                setTimeout(() => loader.remove(), 500);
            }, 300);
        }
        progressBar.style.width = progress + '%';
    }, 200);
}

// ========================================
// FLOATING PARTICLES
// ========================================
function initParticles() {
    const canvas = document.createElement('canvas');
    canvas.className = 'particles-canvas';
    document.body.appendChild(canvas);
    
    const ctx = canvas.getContext('2d');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    const particles = [];
    const particleCount = 100;
    
    class Particle {
        constructor() {
            this.x = Math.random() * canvas.width;
            this.y = Math.random() * canvas.height;
            this.size = Math.random() * 2 + 0.5;
            this.speedX = Math.random() * 0.5 - 0.25;
            this.speedY = Math.random() * 0.5 - 0.25;
            this.color = Math.random() > 0.5 ? '#BB00FF' : '#00F0FF';
            this.opacity = Math.random() * 0.5 + 0.2;
        }
        
        update() {
            this.x += this.speedX;
            this.y += this.speedY;
            
            if (this.x > canvas.width) this.x = 0;
            if (this.x < 0) this.x = canvas.width;
            if (this.y > canvas.height) this.y = 0;
            if (this.y < 0) this.y = canvas.height;
        }
        
        draw() {
            ctx.fillStyle = this.color;
            ctx.globalAlpha = this.opacity;
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
            ctx.fill();
            ctx.globalAlpha = 1;
        }
    }
    
    for (let i = 0; i < particleCount; i++) {
        particles.push(new Particle());
    }
    
    function animate() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        
        particles.forEach(particle => {
            particle.update();
            particle.draw();
        });
        
        // Connect nearby particles
        particles.forEach((a, i) => {
            particles.slice(i + 1).forEach(b => {
                const dx = a.x - b.x;
                const dy = a.y - b.y;
                const distance = Math.sqrt(dx * dx + dy * dy);
                
                if (distance < 100) {
                    ctx.strokeStyle = a.color;
                    ctx.globalAlpha = (1 - distance / 100) * 0.2;
                    ctx.lineWidth = 0.5;
                    ctx.beginPath();
                    ctx.moveTo(a.x, a.y);
                    ctx.lineTo(b.x, b.y);
                    ctx.stroke();
                    ctx.globalAlpha = 1;
                }
            });
        });
        
        requestAnimationFrame(animate);
    }
    
    animate();
    
    window.addEventListener('resize', () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    });
}

// ========================================
// NEON MOUSE TRAIL
// ========================================
function initMouseTrail() {
    const canvas = document.createElement('canvas');
    canvas.className = 'mouse-trail-canvas';
    document.body.appendChild(canvas);
    
    const ctx = canvas.getContext('2d');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    const trail = [];
    const maxTrail = 20;
    
    document.addEventListener('mousemove', (e) => {
        trail.push({
            x: e.clientX,
            y: e.clientY,
            time: Date.now()
        });
        
        if (trail.length > maxTrail) {
            trail.shift();
        }
    });
    
    function drawTrail() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        
        trail.forEach((point, index) => {
            const age = Date.now() - point.time;
            const opacity = 1 - (age / 500);
            const size = 20 - (index / maxTrail) * 15;
            
            if (opacity > 0) {
                const gradient = ctx.createRadialGradient(point.x, point.y, 0, point.x, point.y, size);
                gradient.addColorStop(0, `rgba(187, 0, 255, ${opacity * 0.5})`);
                gradient.addColorStop(0.5, `rgba(0, 240, 255, ${opacity * 0.3})`);
                gradient.addColorStop(1, 'rgba(0, 240, 255, 0)');
                
                ctx.fillStyle = gradient;
                ctx.beginPath();
                ctx.arc(point.x, point.y, size, 0, Math.PI * 2);
                ctx.fill();
            }
        });
        
        requestAnimationFrame(drawTrail);
    }
    
    drawTrail();
    
    window.addEventListener('resize', () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    });
}

// ========================================
// 3D TILT EFFECT ON CARDS
// ========================================
function init3DTilt() {
    const cards = document.querySelectorAll('.feature-card, .benefit-card');
    
    cards.forEach(card => {
        card.addEventListener('mousemove', (e) => {
            const rect = card.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;
            
            const centerX = rect.width / 2;
            const centerY = rect.height / 2;
            
            const rotateX = (y - centerY) / 10;
            const rotateY = (centerX - x) / 10;
            
            card.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateZ(10px)`;
        });
        
        card.addEventListener('mouseleave', () => {
            card.style.transform = 'perspective(1000px) rotateX(0) rotateY(0) translateZ(0)';
        });
    });
}

// ========================================
// TYPEWRITER EFFECT
// ========================================
function initTypewriter() {
    const subtitle = document.querySelector('.hero-subtitle');
    if (!subtitle) return;
    
    const originalText = subtitle.textContent;
    subtitle.textContent = '';
    subtitle.style.opacity = '1';
    
    let i = 0;
    const cursor = document.createElement('span');
    cursor.className = 'typewriter-cursor';
    cursor.textContent = '|';
    subtitle.appendChild(cursor);
    
    function type() {
        if (i < originalText.length) {
            subtitle.textContent = originalText.substring(0, i + 1);
            subtitle.appendChild(cursor);
            i++;
            setTimeout(type, 100);
        } else {
            setTimeout(() => {
                cursor.style.animation = 'blink 1s infinite';
            }, 500);
        }
    }
    
    setTimeout(type, 1000);
}

// ========================================
// ANIMATED COUNTERS
// ========================================
function initAnimatedCounters() {
    // Add stats section if doesn't exist
    const hero = document.querySelector('.hero');
    if (!hero) return;
    
    const stats = document.createElement('div');
    stats.className = 'animated-stats';
    stats.innerHTML = `
        <div class="stat-item">
            <div class="stat-number" data-target="256">0</div>
            <div class="stat-label">Bit AES</div>
        </div>
        <div class="stat-item">
            <div class="stat-number" data-target="10000">0</div>
            <div class="stat-label">PBKDF2 Iterations</div>
        </div>
        <div class="stat-item">
            <div class="stat-number" data-target="6">0</div>
            <div class="stat-label">Languages</div>
        </div>
        <div class="stat-item">
            <div class="stat-number" data-target="100">0</div>
            <div class="stat-label">% Secure</div>
        </div>
    `;
    
    hero.appendChild(stats);
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                animateCounters();
                observer.disconnect();
            }
        });
    });
    
    observer.observe(stats);
    
    function animateCounters() {
        const numbers = stats.querySelectorAll('.stat-number');
        numbers.forEach(num => {
            const target = parseInt(num.dataset.target);
            const duration = 2000;
            const start = Date.now();
            
            function update() {
                const now = Date.now();
                const progress = Math.min((now - start) / duration, 1);
                const current = Math.floor(progress * target);
                
                num.textContent = current.toLocaleString();
                
                if (progress < 1) {
                    requestAnimationFrame(update);
                }
            }
            
            update();
        });
    }
}

// ========================================
// PARALLAX EVERYTHING
// ========================================
function initParallaxEverything() {
    window.addEventListener('scroll', () => {
        const scrolled = window.pageYOffset;
        
        // Parallax hero
        const hero = document.querySelector('.hero-visual');
        if (hero) {
            hero.style.transform = `translateY(${scrolled * 0.3}px)`;
        }
        
        // Parallax sections
        document.querySelectorAll('.feature-card, .benefit-card').forEach((card, index) => {
            const speed = (index % 2 === 0) ? 0.05 : -0.05;
            const rect = card.getBoundingClientRect();
            const offset = (window.innerHeight - rect.top) * speed;
            card.style.transform = `translateY(${offset}px)`;
        });
    });
}

// ========================================
// HOLOGRAPHIC CARDS
// ========================================
function initHolographicCards() {
    const cards = document.querySelectorAll('.feature-card, .benefit-card');
    
    cards.forEach(card => {
        const holoEffect = document.createElement('div');
        holoEffect.className = 'holographic-effect';
        card.appendChild(holoEffect);
        
        card.addEventListener('mousemove', (e) => {
            const rect = card.getBoundingClientRect();
            const x = ((e.clientX - rect.left) / rect.width) * 100;
            const y = ((e.clientY - rect.top) / rect.height) * 100;
            
            holoEffect.style.background = `
                radial-gradient(circle at ${x}% ${y}%, 
                    rgba(187, 0, 255, 0.3) 0%, 
                    rgba(0, 240, 255, 0.2) 50%, 
                    transparent 100%)
            `;
            holoEffect.style.opacity = '1';
        });
        
        card.addEventListener('mouseleave', () => {
            holoEffect.style.opacity = '0';
        });
    });
}

// ========================================
// SMOOTH PAGE TRANSITIONS
// ========================================
function initSmoothTransitions() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            
            if (target) {
                const targetPosition = target.offsetTop - 80;
                const startPosition = window.pageYOffset;
                const distance = targetPosition - startPosition;
                const duration = 1000;
                let start = null;
                
                function animation(currentTime) {
                    if (start === null) start = currentTime;
                    const timeElapsed = currentTime - start;
                    const run = ease(timeElapsed, startPosition, distance, duration);
                    window.scrollTo(0, run);
                    if (timeElapsed < duration) requestAnimationFrame(animation);
                }
                
                function ease(t, b, c, d) {
                    t /= d / 2;
                    if (t < 1) return c / 2 * t * t + b;
                    t--;
                    return -c / 2 * (t * (t - 2) - 1) + b;
                }
                
                requestAnimationFrame(animation);
            }
        });
    });
}

// ========================================
// SCROLL REVEAL ANIMATIONS
// ========================================
const observerOptions = {
    threshold: 0.2,
    rootMargin: '0px 0px -100px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('revealed');
        }
    });
}, observerOptions);

document.querySelectorAll('.feature-card, .benefit-card, .step, .security-item').forEach(el => {
    el.classList.add('reveal-element');
    observer.observe(el);
});

// ========================================
// RANDOM GLITCH EFFECTS
// ========================================
setInterval(() => {
    if (Math.random() > 0.95) {
        const glitchElements = document.querySelectorAll('.section-title, .hero-title');
        glitchElements.forEach(el => {
            el.style.textShadow = `
                ${Math.random() * 10 - 5}px ${Math.random() * 10 - 5}px 10px #BB00FF,
                ${Math.random() * 10 - 5}px ${Math.random() * 10 - 5}px 20px #00F0FF
            `;
            
            setTimeout(() => {
                el.style.textShadow = '';
            }, 50);
        });
    }
}, 2000);

// ========================================
// AMBIENT SOUND (optional - commented out)
// ========================================
/*
function initAmbientSound() {
    const audio = new Audio('path/to/cyberpunk-ambient.mp3');
    audio.loop = true;
    audio.volume = 0.1;
    
    document.addEventListener('click', () => {
        audio.play().catch(() => {});
    }, { once: true });
}
*/

// ========================================
// CONSOLE EASTER EGG
// ========================================
console.log(`
%c
╔══════════════════════════════════════════════════════════════╗
║                                                              ║
║   ██████╗ ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗██╗   ██╗ ║
║  ██╔═══██╗██╔══██╗╚██╗ ██╔╝████╗  ██║╚══██╔══╝██║██║   ██║ ║
║  ██║   ██║██████╔╝ ╚████╔╝ ██╔██╗ ██║   ██║   ██║██║   ██║ ║
║  ██║   ██║██╔══██╗  ╚██╔╝  ██║╚██╗██║   ██║   ██║██║   ██║ ║
║  ╚██████╔╝██║  ██║   ██║   ██║ ╚████║   ██║   ██║╚██████╔╝ ║
║   ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═══╝   ╚═╝   ╚═╝ ╚═════╝  ║
║                                                              ║
║              🔐 KEEP IT YOURS 💜                             ║
║                                                              ║
║         Secure Messaging Platform v1.0.0                     ║
║         Powered by rhei                                      ║
║                                                              ║
║    💡 Tip: Type 'matrix()' for a surprise!                  ║
║                                                              ║
╚══════════════════════════════════════════════════════════════╝

`, 'color: #BB00FF; font-family: monospace; font-size: 10px; line-height: 1.2;');

// Matrix mode easter egg
window.matrix = function() {
    const canvas = document.createElement('canvas');
    canvas.style.position = 'fixed';
    canvas.style.top = '0';
    canvas.style.left = '0';
    canvas.style.width = '100%';
    canvas.style.height = '100%';
    canvas.style.zIndex = '99999';
    canvas.style.pointerEvents = 'none';
    document.body.appendChild(canvas);
    
    const ctx = canvas.getContext('2d');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    const chars = '01ORYNTIUM';
    const fontSize = 14;
    const columns = canvas.width / fontSize;
    const drops = Array(Math.floor(columns)).fill(1);
    
    function draw() {
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
    
    const interval = setInterval(draw, 33);
    
    setTimeout(() => {
        clearInterval(interval);
        canvas.remove();
        console.log('%c✅ Matrix mode deactivated!', 'color: #00FF41; font-size: 16px; font-weight: bold;');
    }, 10000);
    
    console.log('%c🎮 Matrix mode activated! Enjoy 10 seconds of digital rain...', 'color: #00FF41; font-size: 16px; font-weight: bold;');
};







