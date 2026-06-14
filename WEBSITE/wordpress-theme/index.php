<?php
/**
 * The main template file
 *
 * This is the most generic template file in a WordPress theme
 * and one of the two required files for a theme (the other being style.css).
 * It is used to display a page when nothing more specific matches a query.
 *
 * @package ORYNTIUM
 * @since 1.0.0
 */

get_header(); ?>

<div class="scanlines"></div>
<div class="glitch-overlay"></div>

<main id="main" class="site-main">
    
    <?php if (is_front_page()) : ?>
        <!-- Hero Section -->
        <section class="hero-section">
            <div class="container">
                <div class="hero-content">
                    <h1 class="hero-title glitch" data-text="<?php echo esc_attr(__('KEEP IT YOURS', 'oryntium')); ?>">
                        <?php echo __('KEEP IT YOURS', 'oryntium'); ?>
                    </h1>
                    <p class="hero-subtitle">
                        <?php echo __('Najnowocześniejsza platforma szyfrowania SMS w Polsce', 'oryntium'); ?>
                    </p>
                    <p class="hero-description">
                        <?php echo __('Twoje wiadomości pozostają tylko Twoje. Szyfrowanie end-to-end AES-256, indywidualne hasła dla każdego kontaktu, zero chmury.', 'oryntium'); ?>
                    </p>
                    <div class="hero-buttons">
                        <a href="#features" class="btn btn-primary">
                            <span><?php echo __('Odkryj możliwości', 'oryntium'); ?></span>
                            <svg class="btn-icon" viewBox="0 0 24 24" width="20" height="20">
                                <path fill="currentColor" d="M8.59,16.58L13.17,12L8.59,7.41L10,6L16,12L10,18L8.59,16.58Z"/>
                            </svg>
                        </a>
                        <a href="#how-it-works" class="btn btn-secondary">
                            <span><?php echo __('Zobacz jak działa', 'oryntium'); ?></span>
                        </a>
                    </div>
                </div>
                <div class="hero-visual">
                    <div class="rotating-diamond">
                        <svg viewBox="0 0 200 200" width="300" height="300">
                            <defs>
                                <linearGradient id="diamondGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                                    <stop offset="0%" style="stop-color:#BB00FF;stop-opacity:1" />
                                    <stop offset="50%" style="stop-color:#00F0FF;stop-opacity:1" />
                                    <stop offset="100%" style="stop-color:#BB00FF;stop-opacity:1" />
                                </linearGradient>
                                <filter id="glow">
                                    <feGaussianBlur stdDeviation="4" result="coloredBlur"/>
                                    <feMerge>
                                        <feMergeNode in="coloredBlur"/>
                                        <feMergeNode in="SourceGraphic"/>
                                    </feMerge>
                                </filter>
                            </defs>
                            <rect class="diamond-outer" x="50" y="50" width="100" height="100" fill="none" 
                                  stroke="url(#diamondGradient)" stroke-width="3" transform="rotate(45 100 100)" 
                                  filter="url(#glow)"/>
                            <rect class="diamond-inner" x="65" y="65" width="70" height="70" fill="none" 
                                  stroke="url(#diamondGradient)" stroke-width="2" transform="rotate(45 100 100)"
                                  filter="url(#glow)"/>
                            <rect class="diamond-core" x="80" y="80" width="40" height="40" fill="none" 
                                  stroke="url(#diamondGradient)" stroke-width="1.5" transform="rotate(45 100 100)"
                                  filter="url(#glow)"/>
                        </svg>
                    </div>
                </div>
            </div>
        </section>

        <!-- Features Section -->
        <section id="features" class="section features-section">
            <div class="container">
                <h2 class="section-title" data-text="<?php echo esc_attr(__('FUNKCJE', 'oryntium')); ?>">
                    <?php echo __('FUNKCJE', 'oryntium'); ?>
                </h2>
                <div class="features-grid">
                    <?php
                    $features = array(
                        array(
                            'icon' => 'M12,17A2,2 0 0,0 14,15C14,13.89 13.1,13 12,13A2,2 0 0,0 10,15A2,2 0 0,0 12,17M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6A2,2 0 0,1 4,20V10C4,8.89 4.9,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M12,3A3,3 0 0,0 9,6V8H15V6A3,3 0 0,0 12,3Z',
                            'title' => __('Szyfrowanie AES-256', 'oryntium'),
                            'description' => __('Wojskowy standard szyfrowania z algorytmem AES-256-CBC. Każda wiadomość chroniona na poziomie bankowym.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z',
                            'title' => __('Indywidualne hasła', 'oryntium'),
                            'description' => __('Każdy kontakt ma własne unikalne hasło szyfrujące. Maksymalne bezpieczeństwo bez kompromisów.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M17,12V3A1,1 0 0,0 16,2H3A1,1 0 0,0 2,3V17L6,13H16A1,1 0 0,0 17,12M21,6H19V15H6V17A1,1 0 0,0 7,18H18L22,22V7A1,1 0 0,0 21,6Z',
                            'title' => __('Automatyczne przetwarzanie', 'oryntium'),
                            'description' => __('Aplikacja automatycznie szyfruje i deszyfruje wiadomości w tle. Wszystko działa płynnie i bezproblemowo.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M12,1L8,5H11V14H13V5H16M18,23H6C4.89,23 4,22.1 4,21V9A2,2 0 0,1 6,7H9V9H6V21H18V9H15V7H18A2,2 0 0,1 20,9V21A2,2 0 0,1 18,23Z',
                            'title' => __('Zero chmury', 'oryntium'),
                            'description' => __('Wszystkie dane przechowywane lokalnie na Twoim urządzeniu. Brak serwerów zewnętrznych, brak ryzyka wycieku.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M21,16.5C21,16.88 20.79,17.21 20.47,17.38L12.57,21.82C12.41,21.94 12.21,22 12,22C11.79,22 11.59,21.94 11.43,21.82L3.53,17.38C3.21,17.21 3,16.88 3,16.5V7.5C3,7.12 3.21,6.79 3.53,6.62L11.43,2.18C11.59,2.06 11.79,2 12,2C12.21,2 12.41,2.06 12.57,2.18L20.47,6.62C20.79,6.79 21,7.12 21,7.5V16.5M12,4.15L6.04,7.5L12,10.85L17.96,7.5L12,4.15M5,15.91L11,19.29V12.58L5,9.21V15.91M19,15.91V9.21L13,12.58V19.29L19,15.91Z',
                            'title' => __('Ochrona ekranu', 'oryntium'),
                            'description' => __('Automatyczna blokada screenshotów z efektem Matrix. Twoje rozmowy pozostają prywatne.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M12.87,15.07L10.33,12.56L10.36,12.53C12.1,10.59 13.34,8.36 14.07,6H17V4H10V2H8V4H1V6H12.17C11.5,7.92 10.44,9.75 9,11.35C8.07,10.32 7.3,9.19 6.69,8H4.69C5.42,9.63 6.42,11.17 7.67,12.56L2.58,17.58L4,19L9,14L12.11,17.11L12.87,15.07M18.5,10H16.5L12,22H14L15.12,19H19.87L21,22H23L18.5,10M15.88,17L17.5,12.67L19.12,17H15.88Z',
                            'title' => __('6 języków', 'oryntium'),
                            'description' => __('Wsparcie dla Polski, Angielskiego, Hiszpańskiego, Niemieckiego, Rosyjskiego i Chińskiego.', 'oryntium')
                        )
                    );
                    
                    foreach ($features as $feature) :
                    ?>
                    <div class="feature-card">
                    <div class="feature-icon">
                        <?php if ($feature['icon'] === 'M12,17A2,2 0 0,0 14,15C14,13.89 13.1,13 12,13A2,2 0 0,0 10,15A2,2 0 0,0 12,17M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6A2,2 0 0,1 4,20V10C4,8.89 4.9,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M12,3A3,3 0 0,0 9,6V8H15V6A3,3 0 0,0 12,3Z') : ?>
                            <svg viewBox="0 0 24 24" width="48" height="48">
                                <path fill="currentColor" d="M12,17A2,2 0 0,0 14,15C14,13.89 13.1,13 12,13A2,2 0 0,0 10,15A2,2 0 0,0 12,17M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6A2,2 0 0,1 4,20V10C4,8.89 4.9,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M12,3A3,3 0 0,0 9,6V8H15V6A3,3 0 0,0 12,3Z"/>
                            </svg>
                        <?php elseif ($feature['icon'] === 'M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z') : ?>
                            <svg viewBox="0 0 24 24" width="48" height="48">
                                <path fill="currentColor" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                            </svg>
                        <?php elseif ($feature['icon'] === 'M17,12V3A1,1 0 0,0 16,2H3A1,1 0 0,0 2,3V17L6,13H16A1,1 0 0,0 17,12M21,6H19V15H6V17A1,1 0 0,0 7,18H18L22,22V7A1,1 0 0,0 21,6Z') : ?>
                            <svg viewBox="0 0 24 24" width="48" height="48">
                                <path fill="currentColor" d="M17,12V3A1,1 0 0,0 16,2H3A1,1 0 0,0 2,3V17L6,13H16A1,1 0 0,0 17,12M21,6H19V15H6V17A1,1 0 0,0 7,18H18L22,22V7A1,1 0 0,0 21,6Z"/>
                            </svg>
                        <?php elseif ($feature['icon'] === 'M12,1L8,5H11V14H13V5H16M18,23H6C4.89,23 4,22.1 4,21V9A2,2 0 0,1 6,7H9V9H6V21H18V9H15V7H18A2,2 0 0,1 20,9V21A2,2 0 0,1 18,23Z') : ?>
                            <svg viewBox="0 0 24 24" width="48" height="48">
                                <path fill="currentColor" d="M12,1L8,5H11V14H13V5H16M18,23H6C4.89,23 4,22.1 4,21V9A2,2 0 0,1 6,7H9V9H6V21H18V9H15V7H18A2,2 0 0,1 20,9V21A2,2 0 0,1 18,23Z"/>
                            </svg>
                        <?php elseif ($feature['icon'] === 'M21,16.5C21,16.88 20.79,17.21 20.47,17.38L12.57,21.82C12.41,21.94 12.21,22 12,22C11.79,22 11.59,21.94 11.43,21.82L3.53,17.38C3.21,17.21 3,16.88 3,16.5V7.5C3,7.12 3.21,6.79 3.53,6.62L11.43,2.18C11.59,2.06 11.79,2 12,2C12.21,2 12.41,2.06 12.57,2.18L20.47,6.62C20.79,6.79 21,7.12 21,7.5V16.5M12,4.15L6.04,7.5L12,10.85L17.96,7.5L12,4.15M5,15.91L11,19.29V12.58L5,9.21V15.91M19,15.91V9.21L13,12.58V19.29L19,15.91Z') : ?>
                            <svg viewBox="0 0 24 24" width="48" height="48">
                                <path fill="currentColor" d="M21,16.5C21,16.88 20.79,17.21 20.47,17.38L12.57,21.82C12.41,21.94 12.21,22 12,22C11.79,22 11.59,21.94 11.43,21.82L3.53,17.38C3.21,17.21 3,16.88 3,16.5V7.5C3,7.12 3.21,6.79 3.53,6.62L11.43,2.18C11.59,2.06 11.79,2 12,2C12.21,2 12.41,2.06 12.57,2.18L20.47,6.62C20.79,6.79 21,7.12 21,7.5V16.5M12,4.15L6.04,7.5L12,10.85L17.96,7.5L12,4.15M5,15.91L11,19.29V12.58L5,9.21V15.91M19,15.91V9.21L13,12.58V19.29L19,15.91Z"/>
                            </svg>
                        <?php elseif ($feature['icon'] === 'M12.87,15.07L10.33,12.56L10.36,12.53C12.1,10.59 13.34,8.36 14.07,6H17V4H10V2H8V4H1V6H12.17C11.5,7.92 10.44,9.75 9,11.35C8.07,10.32 7.3,9.19 6.69,8H4.69C5.42,9.63 6.42,11.17 7.67,12.56L2.58,17.58L4,19L9,14L12.11,17.11L12.87,15.07M18.5,10H16.5L12,22H14L15.12,19H19.87L21,22H23L18.5,10M15.88,17L17.5,12.67L19.12,17H15.88Z') : ?>
                            <svg viewBox="0 0 24 24" width="48" height="48">
                                <path fill="currentColor" d="M12.87,15.07L10.33,12.56L10.36,12.53C12.1,10.59 13.34,8.36 14.07,6H17V4H10V2H8V4H1V6H12.17C11.5,7.92 10.44,9.75 9,11.35C8.07,10.32 7.3,9.19 6.69,8H4.69C5.42,9.63 6.42,11.17 7.67,12.56L2.58,17.58L4,19L9,14L12.11,17.11L12.87,15.07M18.5,10H16.5L12,22H14L15.12,19H19.87L21,22H23L18.5,10M15.88,17L17.5,12.67L19.12,17H15.88Z"/>
                            </svg>
                        <?php else : ?>
                            <svg viewBox="0 0 24 24" width="48" height="48">
                                <path fill="currentColor" d="<?php echo esc_attr($feature['icon']); ?>"/>
                            </svg>
                        <?php endif; ?>
                    </div>
                        <h3 class="feature-title"><?php echo $feature['title']; ?></h3>
                        <p class="feature-description"><?php echo $feature['description']; ?></p>
                    </div>
                    <?php endforeach; ?>
                </div>
            </div>
        </section>

        <!-- How It Works Section -->
        <section id="how-it-works" class="how-it-works">
            <div class="container">
                <h2 class="section-title" data-text="<?php echo esc_attr(__('JAK TO DZIAŁA', 'oryntium')); ?>">
                    <?php echo __('JAK TO DZIAŁA', 'oryntium'); ?>
                </h2>
                <div class="steps">
                    <div class="step">
                        <div class="step-number">01</div>
                        <div class="step-content">
                            <h3 class="step-title"><?php _e('Utwórz PIN', 'oryntium'); ?></h3>
                            <p class="step-description">
                                <?php _e('Przy pierwszym uruchomieniu ustaw 6-cyfrowy PIN. To Twoja pierwsza linia obrony. Maksymalnie 5 prób logowania dla bezpieczeństwa.', 'oryntium'); ?>
                            </p>
                        </div>
                    </div>

                    <div class="step">
                        <div class="step-number">02</div>
                        <div class="step-content">
                            <h3 class="step-title"><?php _e('Dodaj kontakty', 'oryntium'); ?></h3>
                            <p class="step-description">
                                <?php _e('Dla każdego kontaktu ustaw unikalną nazwę, numer telefonu i hasło szyfrujące. Hasła przechowywane w szyfrowanej bazie danych Room.', 'oryntium'); ?>
                            </p>
                        </div>
                    </div>

                    <div class="step">
                        <div class="step-number">03</div>
                        <div class="step-content">
                            <h3 class="step-title"><?php _e('Wyślij wiadomość', 'oryntium'); ?></h3>
                            <p class="step-description">
                                <?php _e('Wpisz wiadomość, włącz przełącznik szyfrowania i wyślij. Aplikacja automatycznie zaszyfruje treść używając AES-256 z hasłem kontaktu.', 'oryntium'); ?>
                            </p>
                        </div>
                    </div>

                    <div class="step">
                        <div class="step-number">04</div>
                        <div class="step-content">
                            <h3 class="step-title"><?php _e('Odbierz bezpiecznie', 'oryntium'); ?></h3>
                            <p class="step-description">
                                <?php _e('Przychodzące zaszyfrowane SMS-y (z prefiksem SCRYPT:) są automatycznie deszyfrowane. Widzisz czytelną treść, podczas gdy inne aplikacje pokazują szyfr.', 'oryntium'); ?>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Benefits Section -->
        <section id="benefits" class="section">
            <div class="container">
                <h2 class="section-title" data-text="<?php echo esc_attr(__('ZALETY', 'oryntium')); ?>">
                    <?php echo __('ZALETY', 'oryntium'); ?>
                </h2>
                <div class="benefits-grid">
                    <?php
                    $benefits = array(
                        array(
                            'icon' => 'M17,8C17,5.24 14.76,3 12,3C9.24,3 7,5.24 7,8C7,10.76 9.24,13 12,13C14.76,13 17,10.76 17,8M23,16L20.46,13.46L19.41,14.5L23,18.08L19.41,21.67L20.46,22.71L23,20.17L23,16M1,18.08L4.59,14.5L3.54,13.46L1,16L1,20.17L3.54,22.71L4.59,21.67L1,18.08M12,14C9.58,14 5,15.21 5,17.5V19H19V17.5C19,15.21 14.42,14 12,14Z',
                            'title' => __('Pełna prywatność', 'oryntium'),
                            'description' => __('Twoje wiadomości nigdy nie opuszczają Twojego urządzenia w postaci niezaszyfrowanej.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M12,1L3,5V11C3,16.55 6.84,21.74 12,23C17.16,21.74 21,16.55 21,11V5L12,1M12,5A3,3 0 0,1 15,8A3,3 0 0,1 12,11A3,3 0 0,1 9,8A3,3 0 0,1 12,5M17.13,17C15.92,18.85 14.11,20.24 12,20.92C9.89,20.24 8.08,18.85 6.87,17C6.53,16.5 6.24,16 6,15.47C6,13.82 8.71,12.47 12,12.47C15.29,12.47 18,13.79 18,15.47C17.76,16 17.47,16.5 17.13,17Z',
                            'title' => __('Zero wiedzy serwerowej', 'oryntium'),
                            'description' => __('Brak serwerów zewnętrznych oznacza, że nikt oprócz Ciebie nie ma dostępu do danych.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M12,8A4,4 0 0,1 16,12A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10M10,22C9.75,22 9.54,21.82 9.5,21.58L9.13,18.93C8.5,18.68 7.96,18.34 7.44,17.94L4.95,18.95C4.73,19.03 4.46,18.95 4.34,18.73L2.34,15.27C2.21,15.05 2.27,14.78 2.46,14.63L4.57,12.97L4.5,12L4.57,11L2.46,9.37C2.27,9.22 2.21,8.95 2.34,8.73L4.34,5.27C4.46,5.05 4.73,4.96 4.95,5.05L7.44,6.05C7.96,5.66 8.5,5.32 9.13,5.07L9.5,2.42C9.54,2.18 9.75,2 10,2H14C14.25,2 14.46,2.18 14.5,2.42L14.87,5.07C15.5,5.32 16.04,5.66 16.56,6.05L19.05,5.05C19.27,4.96 19.54,5.05 19.66,5.27L21.66,8.73C21.79,8.95 21.73,9.22 21.54,9.37L19.43,11L19.5,12L19.43,13L21.54,14.63C21.73,14.78 21.79,15.05 21.66,15.27L19.66,18.73C19.54,18.95 19.27,19.04 19.05,18.95L16.56,17.95C16.04,18.34 15.5,18.68 14.87,18.93L14.5,21.58C14.46,21.82 14.25,22 14,22H10M11.25,4L10.88,6.61C9.68,6.86 8.62,7.5 7.85,8.39L5.44,7.35L4.69,8.65L6.8,10.2C6.4,11.37 6.4,12.64 6.8,13.8L4.68,15.36L5.43,16.66L7.86,15.62C8.63,16.5 9.68,17.14 10.87,17.38L11.24,20H12.76L13.13,17.39C14.32,17.14 15.37,16.5 16.14,15.62L18.57,16.66L19.32,15.36L17.2,13.81C17.6,12.64 17.6,11.37 17.2,10.2L19.31,8.65L18.56,7.35L16.15,8.39C15.38,7.5 14.32,6.86 13.12,6.62L12.75,4H11.25Z',
                            'title' => __('Pełna kontrola', 'oryntium'),
                            'description' => __('Ty decydujesz, które wiadomości szyfrować i z kim się nimi dzielić.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M13,9H11V7H13M13,17H11V11H13M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z',
                            'title' => __('Bez abonamentów', 'oryntium'),
                            'description' => __('Jednorazowy zakup, brak ukrytych opłat, brak subskrypcji. Pełen dostęp na zawsze.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M17.75,4.09L15.22,6.03L16.13,9.09L13.5,7.28L10.87,9.09L11.78,6.03L9.25,4.09L12.44,4L13.5,1L14.56,4L17.75,4.09M21.25,11L19.61,12.25L20.2,14.23L18.5,13.06L16.8,14.23L17.39,12.25L15.75,11L17.81,10.95L18.5,9L19.19,10.95L21.25,11M18.97,15.95C19.8,15.87 20.69,17.05 20.16,17.8C19.84,18.25 19.5,18.67 19.08,19.07C15.17,23 8.84,23 4.94,19.07C1.03,15.17 1.03,8.83 4.94,4.93C5.34,4.53 5.76,4.17 6.21,3.85C6.96,3.32 8.14,4.21 8.06,5.04C7.79,7.9 8.75,10.87 10.95,13.06C13.14,15.26 16.1,16.22 18.97,15.95M17.33,17.97C14.5,17.81 11.7,16.64 9.53,14.5C7.36,12.31 6.2,9.5 6.04,6.68C3.23,9.82 3.34,14.64 6.35,17.66C9.37,20.67 14.19,20.78 17.33,17.97Z',
                            'title' => __('Ekologiczne', 'oryntium'),
                            'description' => __('Brak serwerów chmurowych = mniejszy ślad węglowy i większa efektywność energetyczna.', 'oryntium')
                        ),
                        array(
                            'icon' => 'M12,1L8,5H11V14H13V5H16M18,23H6C4.89,23 4,22.1 4,21V9A2,2 0 0,1 6,7H9V9H6V21H18V9H15V7H18A2,2 0 0,1 20,9V21A2,2 0 0,1 18,23Z',
                            'title' => __('Nowoczesna technologia', 'oryntium'),
                            'description' => __('Zbudowane na Jetpack Compose, Kotlin i najnowszych standardach Android 14.', 'oryntium')
                        )
                    );
                    
                    foreach ($benefits as $benefit) :
                    ?>
                    <div class="benefit-card">
                    <div class="benefit-icon">
                        <?php if ($benefit['icon'] === 'M17,8C17,5.24 14.76,3 12,3C9.24,3 7,5.24 7,8C7,10.76 9.24,13 12,13C14.76,13 17,10.76 17,8M23,16L20.46,13.46L19.41,14.5L23,18.08L19.41,21.67L20.46,22.71L23,20.17L23,16M1,18.08L4.59,14.5L3.54,13.46L1,16L1,20.17L3.54,22.71L4.59,21.67L1,18.08M12,14C9.58,14 5,15.21 5,17.5V19H19V17.5C19,15.21 14.42,14 12,14Z') : ?>
                            <svg viewBox="0 0 24 24" width="40" height="40">
                                <path fill="currentColor" d="M17,8C17,5.24 14.76,3 12,3C9.24,3 7,5.24 7,8C7,10.76 9.24,13 12,13C14.76,13 17,10.76 17,8M23,16L20.46,13.46L19.41,14.5L23,18.08L19.41,21.67L20.46,22.71L23,20.17L23,16M1,18.08L4.59,14.5L3.54,13.46L1,16L1,20.17L3.54,22.71L4.59,21.67L1,18.08M12,14C9.58,14 5,15.21 5,17.5V19H19V17.5C19,15.21 14.42,14 12,14Z"/>
                            </svg>
                        <?php elseif ($benefit['icon'] === 'M12,1L3,5V11C3,16.55 6.84,21.74 12,23C17.16,21.74 21,16.55 21,11V5L12,1M12,5A3,3 0 0,1 15,8A3,3 0 0,1 12,11A3,3 0 0,1 9,8A3,3 0 0,1 12,5M17.13,17C15.92,18.85 14.11,20.24 12,20.92C9.89,20.24 8.08,18.85 6.87,17C6.53,16.5 6.24,16 6,15.47C6,13.82 8.71,12.47 12,12.47C15.29,12.47 18,13.79 18,15.47C17.76,16 17.47,16.5 17.13,17Z') : ?>
                            <svg viewBox="0 0 24 24" width="40" height="40">
                                <path fill="currentColor" d="M12,1L3,5V11C3,16.55 6.84,21.74 12,23C17.16,21.74 21,16.55 21,11V5L12,1M12,5A3,3 0 0,1 15,8A3,3 0 0,1 12,11A3,3 0 0,1 9,8A3,3 0 0,1 12,5M17.13,17C15.92,18.85 14.11,20.24 12,20.92C9.89,20.24 8.08,18.85 6.87,17C6.53,16.5 6.24,16 6,15.47C6,13.82 8.71,12.47 12,12.47C15.29,12.47 18,13.79 18,15.47C17.76,16 17.47,16.5 17.13,17Z"/>
                            </svg>
                        <?php elseif ($benefit['icon'] === 'M12,8A4,4 0 0,1 16,12A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10M10,22C9.75,22 9.54,21.82 9.5,21.58L9.13,18.93C8.5,18.68 7.96,18.34 7.44,17.94L4.95,18.95C4.73,19.03 4.46,18.95 4.34,18.73L2.34,15.27C2.21,15.05 2.27,14.78 2.46,14.63L4.57,12.97L4.5,12L4.57,11L2.46,9.37C2.27,9.22 2.21,8.95 2.34,8.73L4.34,5.27C4.46,5.05 4.73,4.96 4.95,5.05L7.44,6.05C7.96,5.66 8.5,5.32 9.13,5.07L9.5,2.42C9.54,2.18 9.75,2 10,2H14C14.25,2 14.46,2.18 14.5,2.42L14.87,5.07C15.5,5.32 16.04,5.66 16.56,6.05L19.05,5.05C19.27,4.96 19.54,5.05 19.66,5.27L21.66,8.73C21.79,8.95 21.73,9.22 21.54,9.37L19.43,11L19.5,12L19.43,13L21.54,14.63C21.73,14.78 21.79,15.05 21.66,15.27L19.66,18.73C19.54,18.95 19.27,19.04 19.05,18.95L16.56,17.95C16.04,18.34 15.5,18.68 14.87,18.93L14.5,21.58C14.46,21.82 14.25,22 14,22H10M11.25,4L10.88,6.61C9.68,6.86 8.62,7.5 7.85,8.39L5.44,7.35L4.69,8.65L6.8,10.2C6.4,11.37 6.4,12.64 6.8,13.8L4.68,15.36L5.43,16.66L7.86,15.62C8.63,16.5 9.68,17.14 10.87,17.38L11.24,20H12.76L13.13,17.39C14.32,17.14 15.37,16.5 16.14,15.62L18.57,16.66L19.32,15.36L17.2,13.81C17.6,12.64 17.6,11.37 17.2,10.2L19.31,8.65L18.56,7.35L16.15,8.39C15.38,7.5 14.32,6.86 13.12,6.62L12.75,4H11.25Z') : ?>
                            <svg viewBox="0 0 24 24" width="40" height="40">
                                <path fill="currentColor" d="M12,8A4,4 0 0,1 16,12A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10M10,22C9.75,22 9.54,21.82 9.5,21.58L9.13,18.93C8.5,18.68 7.96,18.34 7.44,17.94L4.95,18.95C4.73,19.03 4.46,18.95 4.34,18.73L2.34,15.27C2.21,15.05 2.27,14.78 2.46,14.63L4.57,12.97L4.5,12L4.57,11L2.46,9.37C2.27,9.22 2.21,8.95 2.34,8.73L4.34,5.27C4.46,5.05 4.73,4.96 4.95,5.05L7.44,6.05C7.96,5.66 8.5,5.32 9.13,5.07L9.5,2.42C9.54,2.18 9.75,2 10,2H14C14.25,2 14.46,2.18 14.5,2.42L14.87,5.07C15.5,5.32 16.04,5.66 16.56,6.05L19.05,5.05C19.27,4.96 19.54,5.05 19.66,5.27L21.66,8.73C21.79,8.95 21.73,9.22 21.54,9.37L19.43,11L19.5,12L19.43,13L21.54,14.63C21.73,14.78 21.79,15.05 21.66,15.27L19.66,18.73C19.54,18.95 19.27,19.04 19.05,18.95L16.56,17.95C16.04,18.34 15.5,18.68 14.87,18.93L14.5,21.58C14.46,21.82 14.25,22 14,22H10M11.25,4L10.88,6.61C9.68,6.86 8.62,7.5 7.85,8.39L5.44,7.35L4.69,8.65L6.8,10.2C6.4,11.37 6.4,12.64 6.8,13.8L4.68,15.36L5.43,16.66L7.86,15.62C8.63,16.5 9.68,17.14 10.87,17.38L11.24,20H12.76L13.13,17.39C14.32,17.14 15.37,16.5 16.14,15.62L18.57,16.66L19.32,15.36L17.2,13.81C17.6,12.64 17.6,11.37 17.2,10.2L19.31,8.65L18.56,7.35L16.15,8.39C15.38,7.5 14.32,6.86 13.12,6.62L12.75,4H11.25Z"/>
                            </svg>
                        <?php elseif ($benefit['icon'] === 'M13,9H11V7H13M13,17H11V11H13M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z') : ?>
                            <svg viewBox="0 0 24 24" width="40" height="40">
                                <path fill="currentColor" d="M13,9H11V7H13M13,17H11V11H13M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z"/>
                            </svg>
                        <?php elseif ($benefit['icon'] === 'M17.75,4.09L15.22,6.03L16.13,9.09L13.5,7.28L10.87,9.09L11.78,6.03L9.25,4.09L12.44,4L13.5,1L14.56,4L17.75,4.09M21.25,11L19.61,12.25L20.2,14.23L18.5,13.06L16.8,14.23L17.39,12.25L15.75,11L17.81,10.95L18.5,9L19.19,10.95L21.25,11M18.97,15.95C19.8,15.87 20.69,17.05 20.16,17.8C19.84,18.25 19.5,18.67 19.08,19.07C15.17,23 8.84,23 4.94,19.07C1.03,15.17 1.03,8.83 4.94,4.93C5.34,4.53 5.76,4.17 6.21,3.85C6.96,3.32 8.14,4.21 8.06,5.04C7.79,7.9 8.75,10.87 10.95,13.06C13.14,15.26 16.1,16.22 18.97,15.95M17.33,17.97C14.5,17.81 11.7,16.64 9.53,14.5C7.36,12.31 6.2,9.5 6.04,6.68C3.23,9.82 3.34,14.64 6.35,17.66C9.37,20.67 14.19,20.78 17.33,17.97Z') : ?>
                            <svg viewBox="0 0 24 24" width="40" height="40">
                                <path fill="currentColor" d="M17.75,4.09L15.22,6.03L16.13,9.09L13.5,7.28L10.87,9.09L11.78,6.03L9.25,4.09L12.44,4L13.5,1L14.56,4L17.75,4.09M21.25,11L19.61,12.25L20.2,14.23L18.5,13.06L16.8,14.23L17.39,12.25L15.75,11L17.81,10.95L18.5,9L19.19,10.95L21.25,11M18.97,15.95C19.8,15.87 20.69,17.05 20.16,17.8C19.84,18.25 19.5,18.67 19.08,19.07C15.17,23 8.84,23 4.94,19.07C1.03,15.17 1.03,8.83 4.94,4.93C5.34,4.53 5.76,4.17 6.21,3.85C6.96,3.32 8.14,4.21 8.06,5.04C7.79,7.9 8.75,10.87 10.95,13.06C13.14,15.26 16.1,16.22 18.97,15.95M17.33,17.97C14.5,17.81 11.7,16.64 9.53,14.5C7.36,12.31 6.2,9.5 6.04,6.68C3.23,9.82 3.34,14.64 6.35,17.66C9.37,20.67 14.19,20.78 17.33,17.97Z"/>
                            </svg>
                        <?php else : ?>
                            <svg viewBox="0 0 24 24" width="40" height="40">
                                <path fill="currentColor" d="<?php echo esc_attr($benefit['icon']); ?>"/>
                            </svg>
                        <?php endif; ?>
                    </div>
                        <h3 class="benefit-title"><?php echo $benefit['title']; ?></h3>
                        <p class="benefit-description"><?php echo $benefit['description']; ?></p>
                    </div>
                    <?php endforeach; ?>
                </div>
            </div>
        </section>

        <!-- Security Section -->
        <section id="security" class="section">
            <div class="container">
                <h2 class="section-title" data-text="<?php echo esc_attr(__('BEZPIECZEŃSTWO', 'oryntium')); ?>">
                    <?php echo __('BEZPIECZEŃSTWO', 'oryntium'); ?>
                </h2>
                <div class="security-content">
                    <div class="security-item">
                        <h3 class="security-subtitle"><?php echo __('Algorytmy szyfrowania', 'oryntium'); ?></h3>
                        <ul class="security-list">
                            <li>
                                <span class="security-label">AES-256-CBC</span>
                                <span class="security-value"><?php echo __('Szyfrowanie wiadomości na poziomie wojskowym', 'oryntium'); ?></span>
                            </li>
                            <li>
                                <span class="security-label">PBKDF2-SHA256</span>
                                <span class="security-value"><?php echo __('10,000 iteracji derywacji kluczy', 'oryntium'); ?></span>
                            </li>
                            <li>
                                <span class="security-label">Base64</span>
                                <span class="security-value"><?php echo __('Bezpieczne kodowanie dla formatu SMS', 'oryntium'); ?></span>
                            </li>
                        </ul>
                    </div>

                    <div class="security-item">
                        <h3 class="security-subtitle"><?php echo __('Ochrona danych', 'oryntium'); ?></h3>
                        <ul class="security-list">
                            <li>
                                <span class="security-label">Room Database</span>
                                <span class="security-value"><?php echo __('Szyfrowana lokalna baza danych', 'oryntium'); ?></span>
                            </li>
                            <li>
                                <span class="security-label">No Cloud Backup</span>
                                <span class="security-value"><?php echo __('allowBackup="false" - zero chmury', 'oryntium'); ?></span>
                            </li>
                            <li>
                                <span class="security-label">Device-specific Keys</span>
                                <span class="security-value"><?php echo __('Klucze unikalne dla każdego urządzenia', 'oryntium'); ?></span>
                            </li>
                        </ul>
                    </div>

                    <div class="security-item">
                        <h3 class="security-subtitle"><?php echo __('Format wiadomości', 'oryntium'); ?></h3>
                        <div class="code-block">
                            <code>SCRYPT:[Base64(IV + Salt + Encrypted Data)]</code>
                        </div>
                        <p class="security-note">
                            <?php echo __('Każda zaszyfrowana wiadomość zawiera unikalny wektor inicjalizacji (IV) i sól (Salt) dla maksymalnego bezpieczeństwa.', 'oryntium'); ?>
                        </p>
                    </div>
                </div>
            </div>
        </section>

    <?php else : ?>
        <!-- Standard WordPress content for other pages -->
        <div class="container">
            <div class="wp-content">
                <?php
                if (have_posts()) :
                    while (have_posts()) :
                        the_post();
                        ?>
                        <article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
                            <header class="entry-header">
                                <h1 class="entry-title"><?php the_title(); ?></h1>
                            </header>
                            
                            <div class="entry-content">
                                <?php
                                the_content();
                                
                                wp_link_pages(array(
                                    'before' => '<div class="page-links">' . __('Pages:', 'oryntium'),
                                    'after'  => '</div>',
                                ));
                                ?>
                            </div>
                        </article>
                        <?php
                    endwhile;
                else :
                    ?>
                    <p><?php echo __('Sorry, no posts found.', 'oryntium'); ?></p>
                    <?php
                endif;
                ?>
            </div>
        </div>
    <?php endif; ?>

</main>

<?php get_footer(); ?>
