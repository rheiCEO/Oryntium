<?php
/**
 * The footer template
 *
 * @package ORYNTIUM
 * @since 1.0.0
 */
?>

<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <div class="footer-logo">
                <?php echo oryntium_get_logo_svg(); ?>
                <div>
                    <div class="footer-brand"><?php bloginfo('name'); ?></div>
                    <div class="footer-tagline"><?php _e('Keep it yours', 'oryntium'); ?></div>
                </div>
            </div>
            
            <div class="footer-info">
                <p class="footer-text">
                    <?php bloginfo('description'); ?>
                </p>
            </div>
            
            <?php if (is_active_sidebar('footer-1')) : ?>
                <div class="footer-widgets">
                    <?php dynamic_sidebar('footer-1'); ?>
                </div>
            <?php endif; ?>
            
            <div class="footer-bottom">
                <div class="footer-copyright">
                    <?php printf(esc_html__('© %s %s. All rights reserved.', 'oryntium'), date('Y'), get_bloginfo('name')); ?>
                </div>
                <div class="footer-credits">
                    <?php _e('Powered by', 'oryntium'); ?> <span class="highlight">rhei</span>
                </div>
            </div>
        </div>
    </div>
</footer>

<?php wp_footer(); ?>
</body>
</html>







