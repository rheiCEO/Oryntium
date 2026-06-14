<?php
/**
 * ORYNTIUM Theme Functions
 * 
 * @package ORYNTIUM
 * @since 1.0.0
 */

// Exit if accessed directly
if (!defined('ABSPATH')) {
    exit;
}

/**
 * Theme Setup
 */
function oryntium_setup() {
    // Make theme available for translation
    load_theme_textdomain('oryntium', get_template_directory() . '/languages');
    
    // Add default posts and comments RSS feed links to head
    add_theme_support('automatic-feed-links');
    
    // Let WordPress manage the document title
    add_theme_support('title-tag');
    
    // Enable support for Post Thumbnails
    add_theme_support('post-thumbnails');
    
    // Register navigation menus
    register_nav_menus(array(
        'primary' => esc_html__('Primary Menu', 'oryntium'),
    ));
    
    // Switch default core markup to output valid HTML5
    add_theme_support('html5', array(
        'search-form',
        'comment-form',
        'comment-list',
        'gallery',
        'caption',
    ));
    
    // Add theme support for Custom Logo
    add_theme_support('custom-logo', array(
        'height'      => 60,
        'width'       => 60,
        'flex-width'  => true,
        'flex-height' => true,
    ));
}
add_action('after_setup_theme', 'oryntium_setup');

/**
 * Enqueue scripts and styles
 */
function oryntium_scripts() {
    // Google Fonts
    wp_enqueue_style('oryntium-fonts', 'https://fonts.googleapis.com/css2?family=Orbitron:wght@400;500;600;700;800;900&family=Rajdhani:wght@300;400;500;600;700&display=swap', array(), null);
    
    // Premium CSS
    wp_enqueue_style('oryntium-premium-style', get_template_directory_uri() . '/assets/css/premium.css', array(), '1.0.0');
    
    // Main JavaScript
    wp_enqueue_script('oryntium-main-script', get_template_directory_uri() . '/assets/js/main.js', array(), '1.0.0', true);
    
    // Premium JavaScript
    wp_enqueue_script('oryntium-premium-script', get_template_directory_uri() . '/assets/js/premium.js', array(), '1.0.0', true);
    
    // Localize script for translations
    wp_localize_script('oryntium-main-script', 'oryntiumL10n', array(
        'ajaxurl' => admin_url('admin-ajax.php'),
        'nonce'   => wp_create_nonce('oryntium-nonce'),
    ));
}
add_action('wp_enqueue_scripts', 'oryntium_scripts');

/**
 * Register widget areas
 */
function oryntium_widgets_init() {
    register_sidebar(array(
        'name'          => esc_html__('Footer', 'oryntium'),
        'id'            => 'footer-1',
        'description'   => esc_html__('Add widgets here to appear in your footer.', 'oryntium'),
        'before_widget' => '<div id="%1$s" class="widget %2$s">',
        'after_widget'  => '</div>',
        'before_title'  => '<h3 class="widget-title">',
        'after_title'   => '</h3>',
    ));
}
add_action('widgets_init', 'oryntium_widgets_init');

/**
 * Custom template tags
 */

// Get logo SVG
function oryntium_get_logo_svg() {
    return '<svg class="logo-icon" viewBox="0 0 100 100" width="40" height="40">
        <defs>
            <linearGradient id="logoGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="0%" style="stop-color:#BB00FF;stop-opacity:1" />
                <stop offset="100%" style="stop-color:#00F0FF;stop-opacity:1" />
            </linearGradient>
        </defs>
        <rect x="30" y="30" width="40" height="40" fill="none" stroke="url(#logoGradient)" stroke-width="3" transform="rotate(45 50 50)"/>
        <rect x="35" y="35" width="30" height="30" fill="none" stroke="url(#logoGradient)" stroke-width="2" transform="rotate(45 50 50)"/>
    </svg>';
}

/**
 * Polylang/WPML Support
 */
function oryntium_language_switcher() {
    if (function_exists('pll_the_languages')) {
        // Polylang
        $languages = pll_the_languages(array('raw' => 1));
        if (!empty($languages)) {
            echo '<div class="language-switcher">';
            foreach ($languages as $lang) {
                $active = $lang['current_lang'] ? 'active' : '';
                echo '<a href="' . esc_url($lang['url']) . '" class="lang-link ' . $active . '">' . esc_html($lang['slug']) . '</a>';
            }
            echo '</div>';
        }
    } elseif (function_exists('icl_get_languages')) {
        // WPML
        $languages = icl_get_languages('skip_missing=0');
        if (!empty($languages)) {
            echo '<div class="language-switcher">';
            foreach ($languages as $lang) {
                $active = $lang['active'] ? 'active' : '';
                echo '<a href="' . esc_url($lang['url']) . '" class="lang-link ' . $active . '">' . esc_html($lang['language_code']) . '</a>';
            }
            echo '</div>';
        }
    }
}

/**
 * Add SVG support
 */
function oryntium_mime_types($mimes) {
    $mimes['svg'] = 'image/svg+xml';
    return $mimes;
}
add_filter('upload_mimes', 'oryntium_mime_types');

/**
 * Remove wp-emoji
 */
remove_action('wp_head', 'print_emoji_detection_script', 7);
remove_action('wp_print_styles', 'print_emoji_styles');

/**
 * Security headers
 */
function oryntium_security_headers() {
    header('X-Content-Type-Options: nosniff');
    header('X-Frame-Options: SAMEORIGIN');
    header('X-XSS-Protection: 1; mode=block');
    header('Referrer-Policy: strict-origin-when-cross-origin');
}
add_action('send_headers', 'oryntium_security_headers');

