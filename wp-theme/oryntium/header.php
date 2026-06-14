<?php
/**
 * The header template
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
    <meta name="description" content="<?php bloginfo('description'); ?>">
    <?php wp_head(); ?>
</head>
<body <?php body_class(); ?>>
<?php wp_body_open(); ?>

<!-- Scanlines Effect -->
<div class="scanlines"></div>
<div class="glitch-overlay"></div>

<!-- Navigation -->
<nav class="nav">
    <div class="container">
        <div class="nav-content">
            <div class="logo">
                <a href="<?php echo esc_url(home_url('/')); ?>">
                    <?php
                    if (has_custom_logo()) {
                        the_custom_logo();
                    } else {
                        echo oryntium_get_logo_svg();
                    }
                    ?>
                    <span class="logo-text" data-text="<?php bloginfo('name'); ?>"><?php bloginfo('name'); ?></span>
                </a>
            </div>
            <div class="nav-links">
                <?php
                wp_nav_menu(array(
                    'theme_location' => 'primary',
                    'menu_class'     => 'primary-menu',
                    'container'      => false,
                    'fallback_cb'    => false,
                ));
                
                // Language switcher
                oryntium_language_switcher();
                ?>
            </div>
        </div>
    </div>
</nav>







