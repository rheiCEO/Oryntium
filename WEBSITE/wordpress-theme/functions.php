<?php
/**
 * ORYNTIUM Theme functions and definitions
 *
 * @package ORYNTIUM
 * @since 1.0.0
 */

// Prevent direct access
if (!defined('ABSPATH')) {
    exit;
}

/**
 * Sets up theme defaults and registers support for various WordPress features.
 */
function oryntium_setup() {
    // Add default posts and comments RSS feed links to head
    add_theme_support('automatic-feed-links');

    // Let WordPress manage the document title
    add_theme_support('title-tag');

    // Enable support for Post Thumbnails on posts and pages
    add_theme_support('post-thumbnails');

    // Switch default core markup for search form, comment form, and comments to output valid HTML5
    add_theme_support('html5', array(
        'search-form',
        'comment-form',
        'comment-list',
        'gallery',
        'caption',
        'style',
        'script',
    ));

    // Add theme support for selective refresh for widgets
    add_theme_support('customize-selective-refresh-widgets');

    // Add support for responsive embedded content
    add_theme_support('responsive-embeds');

    // Add support for custom logo
    add_theme_support('custom-logo', array(
        'height'      => 60,
        'width'       => 60,
        'flex-width'  => true,
        'flex-height' => true,
    ));

    // Add support for wide and full alignment
    add_theme_support('align-wide');

    // Add support for editor styles
    add_theme_support('editor-styles');
    add_editor_style('editor-style.css');

    // Register navigation menus
    register_nav_menus(array(
        'primary' => __('Primary Menu', 'oryntium'),
        'footer'  => __('Footer Menu', 'oryntium'),
    ));

    // Add support for custom background
    add_theme_support('custom-background', array(
        'default-color' => '0A0A0A',
    ));
}
add_action('after_setup_theme', 'oryntium_setup');

/**
 * Set the content width in pixels, based on the theme's design and stylesheet.
 */
function oryntium_content_width() {
    $GLOBALS['content_width'] = apply_filters('oryntium_content_width', 1200);
}
add_action('after_setup_theme', 'oryntium_content_width', 0);

/**
 * Register widget areas.
 */
function oryntium_widgets_init() {
    register_sidebar(array(
        'name'          => __('Sidebar', 'oryntium'),
        'id'            => 'sidebar-1',
        'description'   => __('Add widgets here.', 'oryntium'),
        'before_widget' => '<section id="%1$s" class="widget %2$s">',
        'after_widget'  => '</section>',
        'before_title'  => '<h2 class="widget-title">',
        'after_title'   => '</h2>',
    ));

    register_sidebar(array(
        'name'          => __('Footer Widgets', 'oryntium'),
        'id'            => 'footer-widgets',
        'description'   => __('Add footer widgets here.', 'oryntium'),
        'before_widget' => '<div id="%1$s" class="footer-widget %2$s">',
        'after_widget'  => '</div>',
        'before_title'  => '<h3 class="footer-widget-title">',
        'after_title'   => '</h3>',
    ));
}
add_action('widgets_init', 'oryntium_widgets_init');

/**
 * Enqueue scripts and styles.
 */
function oryntium_scripts() {
    // Theme stylesheet
    wp_enqueue_style('oryntium-style', get_stylesheet_uri(), array(), wp_get_theme()->get('Version'));

    // Theme JavaScript
    wp_enqueue_script('oryntium-script', get_template_directory_uri() . '/js/theme.js', array(), wp_get_theme()->get('Version'), true);

    // Comment reply script
    if (is_singular() && comments_open() && get_option('thread_comments')) {
        wp_enqueue_script('comment-reply');
    }

    // Localize script for AJAX
    wp_localize_script('oryntium-script', 'oryntium_ajax', array(
        'ajax_url' => admin_url('admin-ajax.php'),
        'nonce'    => wp_create_nonce('oryntium_nonce'),
        'strings'  => array(
            'loading' => __('Loading...', 'oryntium'),
            'error'   => __('An error occurred. Please try again.', 'oryntium'),
        ),
    ));
}
add_action('wp_enqueue_scripts', 'oryntium_scripts');

/**
 * Fallback menu for primary navigation
 */
function oryntium_fallback_menu() {
    echo '<ul id="primary-menu" class="menu">';
    echo '<li><a href="' . esc_url(home_url('/')) . '">' . __('Home', 'oryntium') . '</a></li>';
    
    // Get pages for menu
    $pages = get_pages(array(
        'sort_column' => 'menu_order',
        'sort_order'  => 'ASC',
        'number'      => 5,
    ));
    
    foreach ($pages as $page) {
        echo '<li><a href="' . esc_url(get_permalink($page->ID)) . '">' . esc_html($page->post_title) . '</a></li>';
    }
    
    echo '</ul>';
}

/**
 * Custom excerpt length
 */
function oryntium_excerpt_length($length) {
    return 30;
}
add_filter('excerpt_length', 'oryntium_excerpt_length', 999);

/**
 * Custom excerpt more
 */
function oryntium_excerpt_more($more) {
    return '...';
}
add_filter('excerpt_more', 'oryntium_excerpt_more');

/**
 * Add custom body classes
 */
function oryntium_body_classes($classes) {
    // Add class for admin bar
    if (is_admin_bar_showing()) {
        $classes[] = 'admin-bar';
    }

    // Add class for homepage
    if (is_front_page()) {
        $classes[] = 'homepage';
    }

    // Add class for mobile
    if (wp_is_mobile()) {
        $classes[] = 'mobile';
    }

    return $classes;
}
add_filter('body_class', 'oryntium_body_classes');

/**
 * Customize the login page
 */
function oryntium_login_styles() {
    wp_enqueue_style('oryntium-login', get_template_directory_uri() . '/css/login.css', array(), wp_get_theme()->get('Version'));
}
add_action('login_enqueue_scripts', 'oryntium_login_styles');

/**
 * Change login logo URL
 */
function oryntium_login_logo_url() {
    return home_url();
}
add_filter('login_headerurl', 'oryntium_login_logo_url');

/**
 * Change login logo title
 */
function oryntium_login_logo_url_title() {
    return get_bloginfo('name');
}
add_filter('login_headertitle', 'oryntium_login_logo_url_title');

/**
 * Add security headers
 */
function oryntium_security_headers() {
    if (!is_admin()) {
        header('X-Content-Type-Options: nosniff');
        header('X-Frame-Options: SAMEORIGIN');
        header('X-XSS-Protection: 1; mode=block');
        header('Referrer-Policy: strict-origin-when-cross-origin');
        header('Permissions-Policy: camera=(), microphone=(), geolocation=()');
    }
}
add_action('send_headers', 'oryntium_security_headers');

/**
 * Optimize WordPress performance
 */
function oryntium_performance_optimizations() {
    // Remove unnecessary WordPress features
    remove_action('wp_head', 'wp_generator');
    remove_action('wp_head', 'wlwmanifest_link');
    remove_action('wp_head', 'rsd_link');
    remove_action('wp_head', 'wp_shortlink_wp_head');
    remove_action('wp_head', 'adjacent_posts_rel_link_wp_head');
    
    // Remove emoji scripts
    remove_action('wp_head', 'print_emoji_detection_script', 7);
    remove_action('wp_print_styles', 'print_emoji_styles');
    remove_action('admin_print_scripts', 'print_emoji_detection_script');
    remove_action('admin_print_styles', 'print_emoji_styles');
    
    // Remove block library CSS
    wp_dequeue_style('wp-block-library');
    wp_dequeue_style('wp-block-library-theme');
    wp_dequeue_style('wc-block-style');
}
add_action('init', 'oryntium_performance_optimizations');

/**
 * Add theme customizer options
 */
function oryntium_customize_register($wp_customize) {
    // Add ORYNTIUM section
    $wp_customize->add_section('oryntium_options', array(
        'title'    => __('ORYNTIUM Options', 'oryntium'),
        'priority' => 30,
    ));

    // Hero title setting
    $wp_customize->add_setting('oryntium_hero_title', array(
        'default'           => __('KEEP IT YOURS', 'oryntium'),
        'sanitize_callback' => 'sanitize_text_field',
    ));

    $wp_customize->add_control('oryntium_hero_title', array(
        'label'   => __('Hero Title', 'oryntium'),
        'section' => 'oryntium_options',
        'type'    => 'text',
    ));

    // Hero subtitle setting
    $wp_customize->add_setting('oryntium_hero_subtitle', array(
        'default'           => __('Najnowocześniejsza platforma szyfrowania SMS w Polsce', 'oryntium'),
        'sanitize_callback' => 'sanitize_text_field',
    ));

    $wp_customize->add_control('oryntium_hero_subtitle', array(
        'label'   => __('Hero Subtitle', 'oryntium'),
        'section' => 'oryntium_options',
        'type'    => 'text',
    ));

    // Hero description setting
    $wp_customize->add_setting('oryntium_hero_description', array(
        'default'           => __('Twoje wiadomości pozostają tylko Twoje. Szyfrowanie end-to-end AES-256, indywidualne hasła dla każdego kontaktu, zero chmury.', 'oryntium'),
        'sanitize_callback' => 'sanitize_textarea_field',
    ));

    $wp_customize->add_control('oryntium_hero_description', array(
        'label'   => __('Hero Description', 'oryntium'),
        'section' => 'oryntium_options',
        'type'    => 'textarea',
    ));
}
add_action('customize_register', 'oryntium_customize_register');

/**
 * Load theme textdomain for translations
 */
function oryntium_load_textdomain() {
    load_theme_textdomain('oryntium', get_template_directory() . '/languages');
}
add_action('after_setup_theme', 'oryntium_load_textdomain');

/**
 * Add PWA support
 */
function oryntium_pwa_support() {
    // Add PWA meta tags
    echo '<meta name="apple-mobile-web-app-capable" content="yes">';
    echo '<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">';
    echo '<meta name="apple-mobile-web-app-title" content="' . esc_attr(get_bloginfo('name')) . '">';
    echo '<meta name="theme-color" content="#BB00FF">';
}
add_action('wp_head', 'oryntium_pwa_support');

/**
 * Custom post type for features
 */
function oryntium_register_post_types() {
    // Features post type
    register_post_type('oryntium_feature', array(
        'labels' => array(
            'name'          => __('Features', 'oryntium'),
            'singular_name' => __('Feature', 'oryntium'),
        ),
        'public'       => false,
        'show_ui'      => true,
        'show_in_menu' => true,
        'menu_icon'    => 'dashicons-admin-tools',
        'supports'     => array('title', 'editor', 'thumbnail'),
        'has_archive'  => false,
    ));

    // Benefits post type
    register_post_type('oryntium_benefit', array(
        'labels' => array(
            'name'          => __('Benefits', 'oryntium'),
            'singular_name' => __('Benefit', 'oryntium'),
        ),
        'public'       => false,
        'show_ui'      => true,
        'show_in_menu' => true,
        'menu_icon'    => 'dashicons-star-filled',
        'supports'     => array('title', 'editor', 'thumbnail'),
        'has_archive'  => false,
    ));
}
add_action('init', 'oryntium_register_post_types');

/**
 * Add custom fields for features and benefits
 */
function oryntium_add_meta_boxes() {
    add_meta_box(
        'oryntium_feature_meta',
        __('Feature Details', 'oryntium'),
        'oryntium_feature_meta_callback',
        'oryntium_feature',
        'normal',
        'high'
    );

    add_meta_box(
        'oryntium_benefit_meta',
        __('Benefit Details', 'oryntium'),
        'oryntium_benefit_meta_callback',
        'oryntium_benefit',
        'normal',
        'high'
    );
}
add_action('add_meta_boxes', 'oryntium_add_meta_boxes');

function oryntium_feature_meta_callback($post) {
    wp_nonce_field('oryntium_feature_meta_nonce', 'oryntium_feature_meta_nonce');
    $icon = get_post_meta($post->ID, '_oryntium_feature_icon', true);
    ?>
    <table class="form-table">
        <tr>
            <th><label for="oryntium_feature_icon"><?php echo __('Icon SVG Path', 'oryntium'); ?></label></th>
            <td><textarea id="oryntium_feature_icon" name="oryntium_feature_icon" rows="3" cols="50"><?php echo esc_textarea($icon); ?></textarea></td>
        </tr>
    </table>
    <?php
}

function oryntium_benefit_meta_callback($post) {
    wp_nonce_field('oryntium_benefit_meta_nonce', 'oryntium_benefit_meta_nonce');
    $icon = get_post_meta($post->ID, '_oryntium_benefit_icon', true);
    ?>
    <table class="form-table">
        <tr>
            <th><label for="oryntium_benefit_icon"><?php echo __('Icon SVG Path', 'oryntium'); ?></label></th>
            <td><textarea id="oryntium_benefit_icon" name="oryntium_benefit_icon" rows="3" cols="50"><?php echo esc_textarea($icon); ?></textarea></td>
        </tr>
    </table>
    <?php
}

function oryntium_save_meta_boxes($post_id) {
    // Feature meta
    if (isset($_POST['oryntium_feature_meta_nonce']) && wp_verify_nonce($_POST['oryntium_feature_meta_nonce'], 'oryntium_feature_meta_nonce')) {
        if (isset($_POST['oryntium_feature_icon'])) {
            update_post_meta($post_id, '_oryntium_feature_icon', sanitize_textarea_field($_POST['oryntium_feature_icon']));
        }
    }

    // Benefit meta
    if (isset($_POST['oryntium_benefit_meta_nonce']) && wp_verify_nonce($_POST['oryntium_benefit_meta_nonce'], 'oryntium_benefit_meta_nonce')) {
        if (isset($_POST['oryntium_benefit_icon'])) {
            update_post_meta($post_id, '_oryntium_benefit_icon', sanitize_textarea_field($_POST['oryntium_benefit_icon']));
        }
    }
}
add_action('save_post', 'oryntium_save_meta_boxes');

/**
 * WPML/Polylang compatibility
 */
function oryntium_wpml_compatibility() {
    // Make sure WPML/Polylang strings are translatable
    if (function_exists('pll_register_string')) {
        pll_register_string('Hero Title', 'KEEP IT YOURS', 'oryntium');
        pll_register_string('Hero Subtitle', 'Najnowocześniejsza platforma szyfrowania SMS w Polsce', 'oryntium');
        pll_register_string('Hero Description', 'Twoje wiadomości pozostają tylko Twoje. Szyfrowanie end-to-end AES-256, indywidualne hasła dla każdego kontaktu, zero chmury.', 'oryntium');
    }

    if (function_exists('icl_register_string')) {
        icl_register_string('oryntium', 'Hero Title', 'KEEP IT YOURS');
        icl_register_string('oryntium', 'Hero Subtitle', 'Najnowocześniejsza platforma szyfrowania SMS w Polsce');
        icl_register_string('oryntium', 'Hero Description', 'Twoje wiadomości pozostają tylko Twoje. Szyfrowanie end-to-end AES-256, indywidualne hasła dla każdego kontaktu, zero chmury.');
    }
}
add_action('init', 'oryntium_wpml_compatibility');

/**
 * Add custom CSS for admin
 */
function oryntium_admin_styles() {
    echo '<style>
        .oryntium-admin-logo {
            background: linear-gradient(135deg, #BB00FF, #00F0FF);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            font-family: "Orbitron", sans-serif;
            font-weight: 900;
            text-transform: uppercase;
            letter-spacing: 2px;
        }
    </style>';
}
add_action('admin_head', 'oryntium_admin_styles');

/**
 * Add custom dashboard widget
 */
function oryntium_dashboard_widget() {
    wp_add_dashboard_widget(
        'oryntium_dashboard_widget',
        '<span class="oryntium-admin-logo">ORYNTIUM</span> ' . __('Dashboard', 'oryntium'),
        'oryntium_dashboard_widget_content'
    );
}
add_action('wp_dashboard_setup', 'oryntium_dashboard_widget');

function oryntium_dashboard_widget_content() {
    echo '<div style="text-align: center; padding: 20px;">';
    echo '<h3>' . __('Welcome to ORYNTIUM Theme!', 'oryntium') . '</h3>';
    echo '<p>' . __('Your secure messaging platform website is ready.', 'oryntium') . '</p>';
    echo '<p><strong>' . __('Keep it yours!', 'oryntium') . '</strong></p>';
    echo '</div>';
}


