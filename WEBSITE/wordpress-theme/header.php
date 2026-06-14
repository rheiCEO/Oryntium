<?php
/**
 * The header for our theme
 *
 * This is the template that displays all of the <head> section and everything up until <div id="content">
 *
 * @package ORYNTIUM
 * @since 1.0.0
 */
?>
<!DOCTYPE html>
<html <?php language_attributes(); ?>>
<head>
    <meta charset="<?php bloginfo('charset'); ?>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <?php
    // Custom meta description for homepage
    if (is_front_page()) {
        echo '<meta name="description" content="' . esc_attr__('ORYNTIUM - Najlepsze szyfrowanie SMS w Polsce. Twoje wiadomości, Twoja prywatność.', 'oryntium') . '">';
        echo '<meta name="keywords" content="' . esc_attr__('szyfrowanie SMS, bezpieczne wiadomości, AES-256, prywatność, encryption, secure messaging, oryntium', 'oryntium') . '">';
    } else {
        echo '<meta name="description" content="' . esc_attr(get_bloginfo('description')) . '">';
    }
    ?>
    
    <meta name="author" content="<?php echo esc_attr__('ORYNTIUM powered by rhei', 'oryntium'); ?>">
    
    <!-- Open Graph -->
    <meta property="og:type" content="website">
    <meta property="og:title" content="<?php echo esc_attr(is_front_page() ? __('ORYNTIUM - Secure Messaging Platform', 'oryntium') : wp_get_document_title()); ?>">
    <meta property="og:description" content="<?php echo esc_attr(is_front_page() ? __('Najnowocześniejsza platforma szyfrowania SMS w Polsce. Keep it yours.', 'oryntium') : get_bloginfo('description')); ?>">
    <meta property="og:url" content="<?php echo esc_url(home_url('/')); ?>">
    <meta property="og:site_name" content="<?php echo esc_attr(get_bloginfo('name')); ?>">
    
    <!-- Twitter Card -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="<?php echo esc_attr(is_front_page() ? __('ORYNTIUM - Secure Messaging', 'oryntium') : wp_get_document_title()); ?>">
    <meta name="twitter:description" content="<?php echo esc_attr(is_front_page() ? __('Keep it yours. Najnowocześniejsze szyfrowanie SMS.', 'oryntium') : get_bloginfo('description')); ?>">
    
    <!-- PWA -->
    <link rel="manifest" href="<?php echo get_template_directory_uri(); ?>/manifest.json">
    <meta name="theme-color" content="#BB00FF">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-mobile-web-app-title" content="<?php echo esc_attr(get_bloginfo('name')); ?>">
    
    <!-- Favicon -->
    <link rel="icon" type="image/svg+xml" href="<?php echo get_template_directory_uri(); ?>/favicon.svg">
    
    <title><?php wp_title('|', true, 'right'); ?><?php bloginfo('name'); ?></title>
    
    <?php wp_head(); ?>
    
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Orbitron:wght@400;500;600;700;800;900&family=Rajdhani:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>

<body <?php body_class(); ?>>
<?php wp_body_open(); ?>

<div id="page" class="site">
    <a class="skip-link screen-reader-text" href="#main"><?php echo __('Skip to content', 'oryntium'); ?></a>

    <header id="masthead" class="site-header">
        <div class="container">
            <div class="header-content">
                <div class="site-branding">
                    <a href="<?php echo esc_url(home_url('/')); ?>" rel="home">
                        <svg class="site-logo" viewBox="0 0 100 100" width="40" height="40">
                            <defs>
                                <linearGradient id="logoGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                                    <stop offset="0%" style="stop-color:#BB00FF;stop-opacity:1" />
                                    <stop offset="100%" style="stop-color:#00F0FF;stop-opacity:1" />
                                </linearGradient>
                            </defs>
                            <rect x="30" y="30" width="40" height="40" fill="none" stroke="url(#logoGradient)" stroke-width="3" transform="rotate(45 50 50)"/>
                            <rect x="35" y="35" width="30" height="30" fill="none" stroke="url(#logoGradient)" stroke-width="2" transform="rotate(45 50 50)"/>
                        </svg>
                        <h1 class="site-title" data-text="<?php echo esc_attr(get_bloginfo('name')); ?>">
                            <?php bloginfo('name'); ?>
                        </h1>
                    </a>
                </div>

                <!-- Language Switcher -->
                <div class="language-switcher">
                    <button class="language-toggle" onclick="toggleLanguages()">
                        <svg viewBox="0 0 24 24" width="20" height="20">
                            <path fill="currentColor" d="M9,4V7H10.21L8.5,13.5V17H15.5V13.5L13.79,7H15V4H9M12,8.91L13.5,11H10.5L12,8.91Z"/>
                        </svg>
                        🇵🇱 PL
                    </button>
                    <div class="language-dropdown" id="languageDropdown">
                        <a href="#" onclick="changeLanguage('pl_PL')" class="language-option">🇵🇱 PL</a>
                        <a href="#" onclick="changeLanguage('en_US')" class="language-option">🇺🇸 EN</a>
                        <a href="#" onclick="changeLanguage('es_ES')" class="language-option">🇪🇸 ES</a>
                        <a href="#" onclick="changeLanguage('de_DE')" class="language-option">🇩🇪 DE</a>
                        <a href="#" onclick="changeLanguage('hi_IN')" class="language-option">🇮🇳 HI</a>
                        <a href="#" onclick="changeLanguage('ar')" class="language-option">🇸🇦 AR</a>
                        <a href="#" onclick="changeLanguage('fr_FR')" class="language-option">🇫🇷 FR</a>
                        <a href="#" onclick="changeLanguage('zh_CN')" class="language-option">🇨🇳 ZH</a>
                    </div>
                </div>
                
                <!-- Polylang/WPML Support (if plugins are installed) -->
                <?php if (function_exists('pll_the_languages')) : ?>
                    <div class="polylang-switcher">
                        <?php pll_the_languages(array('show_flags' => 1, 'show_names' => 1, 'display_names_as' => 'slug')); ?>
                    </div>
                <?php elseif (function_exists('icl_get_languages')) : ?>
                    <div class="wpml-switcher">
                        <?php
                        $languages = icl_get_languages('skip_missing=0&orderby=code');
                        if (!empty($languages)) {
                            foreach ($languages as $l) {
                                if (!$l['active']) {
                                    echo '<a href="' . $l['url'] . '">' . strtoupper($l['code']) . '</a>';
                                } else {
                                    echo '<span class="current-language">' . strtoupper($l['code']) . '</span>';
                                }
                            }
                        }
                        ?>
                    </div>
                <?php endif; ?>

                <!-- Mobile Menu Toggle -->
                <button class="menu-toggle" aria-controls="primary-menu" aria-expanded="false">
                    <?php echo __('Menu', 'oryntium'); ?>
                </button>

                <!-- Main Navigation -->
                <nav id="site-navigation" class="main-navigation">
                    <?php
                    wp_nav_menu(array(
                        'theme_location' => 'primary',
                        'menu_id'        => 'primary-menu',
                        'container'      => false,
                        'fallback_cb'    => 'oryntium_fallback_menu',
                    ));
                    ?>
                </nav>
            </div>
        </div>
    </header>

    <div id="content" class="site-content">
